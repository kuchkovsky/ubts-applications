package ua.org.ubts.applications.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ua.org.ubts.applications.converter.StudentConverter;
import ua.org.ubts.applications.dto.StudentDto;
import ua.org.ubts.applications.dto.StudentFullNameDto;
import ua.org.ubts.applications.dto.UuidDto;
import ua.org.ubts.applications.entity.FriendFeedbackEntity;
import ua.org.ubts.applications.entity.PastorFeedbackEntity;
import ua.org.ubts.applications.entity.StudentEntity;
import ua.org.ubts.applications.exception.StudentAlreadyExistsException;
import ua.org.ubts.applications.exception.StudentFeedbackAlreadyExistsException;
import ua.org.ubts.applications.exception.StudentNotFoundException;
import ua.org.ubts.applications.repository.*;
import ua.org.ubts.applications.service.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class StudentServiceImpl implements StudentService {

    private static final String STUDENT_ID_NOT_FOUND_MESSAGE = "Could not find student with id=";
    private static final String STUDENT_ALREADY_EXISTS_MESSAGE = "Student with name='%s %s %s' already exists";
    private static final String PASTOR_FEEDBACK_ALREADY_EXISTS_MESSAGE = "Pastor feedback already exists for this student";
    private static final String FRIEND_FEEDBACK_ALREADY_EXISTS_MESSAGE = "Friend feedback already exists for this student";
    private static final String SEND_MAIL_ERROR_MESSAGE = "Could not send email";

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private MaritalStatusRepository maritalStatusRepository;

    @Autowired
    private ChurchMinistryTypeRepository churchMinistryTypeRepository;

    @Autowired
    private YearRepository yearRepository;

    @Autowired
    private HowFindOutRepository howFindOutRepository;

    @Autowired
    private StudentConverter studentConverter;

    @Autowired
    private StudentFilesService studentFilesService;

    @Value("${UBTS_APPLICATIONS_SERVER_URL}")
    private String serverUrl;

    @Value("${UBTS_APPLICATIONS_SERVER_GMAIL_USERNAME}")
    private String serverGmailUsername;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    public JavaMailSender emailSender;


    private Long saveToDb(StudentEntity studentEntity) {
        programRepository.findByNameAndInfo(studentEntity.getProgram().getName(), studentEntity.getProgram().getInfo())
                .ifPresent(studentEntity::setProgram);
        countryRepository.findByName(studentEntity.getResidence().getCountry().getName())
                .ifPresent(country -> studentEntity.getResidence().setCountry(country));
        educationRepository.findByName(studentEntity.getEducation().getName())
                .ifPresent(studentEntity::setEducation);
        yearRepository.findByValue(studentEntity.getEntryYear().getValue())
                .ifPresent(studentEntity::setEntryYear);
        maritalStatusRepository.findByName(studentEntity.getMaritalData().getStatus().getName())
                .ifPresent(maritalStatus -> studentEntity.getMaritalData().setStatus(maritalStatus));
        if (studentEntity.getChurchData() != null && studentEntity.getChurchMinistry() != null) {
            churchMinistryTypeRepository.findByName(studentEntity.getChurchMinistry().getType().getName())
                    .ifPresent(churchMinistryType -> studentEntity.getChurchMinistry().setType(churchMinistryType));
        }
        howFindOutRepository.findByName(studentEntity.getHowFindOut().getName())
                .ifPresent(studentEntity::setHowFindOut);
        return studentRepository.saveAndFlush(studentEntity).getId();
    }

    private void sendEmailToUser(String userEmail, String uuid) {
        taskExecutor.execute(() -> {
            try {
                MimeMessage mimeMessage = emailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
                String htmlMessage =
                        "<a href=\"" + serverUrl + "/pastor/" + uuid + "\">Анкета пастора</a>"
                        + "<br>"
                        + "<a href=\"" + serverUrl + "/friend/" + uuid + "\">Анкета друга</a>";
                mimeMessage.setContent(htmlMessage, "text/html; charset=UTF-8");
                helper.setTo(userEmail);
                helper.setSubject("Посилання для реєстрації");
                helper.setFrom(serverGmailUsername + "@gmail.com");
                emailSender.send(mimeMessage);
            } catch (MessagingException e) {
                log.error(SEND_MAIL_ERROR_MESSAGE, e);
            }
        });
    }

    @Override
    public StudentEntity getStudent(Long id) {
        return studentRepository.findById(id).orElseThrow(() ->
                new StudentNotFoundException(STUDENT_ID_NOT_FOUND_MESSAGE + id));
    }

    @Override
    public StudentEntity getStudent(String uuid) {
        return studentRepository.findByUuid(uuid).orElseThrow(() ->
                new StudentNotFoundException(STUDENT_ID_NOT_FOUND_MESSAGE + uuid));
    }

    @Override
    public StudentFullNameDto getStudentFullName(String uuid) {
        StudentEntity studentEntity = getStudent(uuid);
        String fullName = studentEntity.getLastName()
                + " " + studentEntity.getFirstName() + " " + studentEntity.getMiddleName();
        return new StudentFullNameDto(fullName);
    }

    @Override
    public List<StudentEntity> getStudents() {
        return studentRepository.findAll();
    }

    @Override
    public List<StudentEntity> getStudents(Optional<List<Integer>> years) {
        return years.map(studentRepository::findByEntryYears).orElseGet(() -> studentRepository.findAll());
    }

    @Override
    public UuidDto createStudent(StudentDto studentDto) {
        StudentEntity studentEntity = studentConverter.convertToEntity(studentDto);
        studentRepository.findByFullName(studentEntity.getFirstName(), studentEntity.getMiddleName(),
                studentEntity.getLastName()).ifPresent(student -> {
            throw new StudentAlreadyExistsException(String.format(STUDENT_ALREADY_EXISTS_MESSAGE,
                    student.getFirstName(), student.getMiddleName(), student.getLastName()));
        });
        String uuid = UUID.randomUUID().toString();
        studentEntity.setUuid(uuid);
        Long id = saveToDb(studentEntity);
        studentFilesService.saveStudentFiles(id, studentDto.getFiles());
        sendEmailToUser(studentEntity.getEmail(), uuid);
        return new UuidDto(uuid);
    }

    @Override
    public void createStudentPastorFeedback(String studentId, PastorFeedbackEntity pastorFeedbackEntity) {
        StudentEntity studentEntity = getStudent(studentId);
        if (Boolean.TRUE.equals(studentEntity.getPastorFeedbackUploaded())) {
            throw new StudentFeedbackAlreadyExistsException(PASTOR_FEEDBACK_ALREADY_EXISTS_MESSAGE);
        }
        studentEntity.setPastorFeedback(pastorFeedbackEntity);
        studentEntity.setPastorFeedbackUploaded(true);
        studentRepository.save(studentEntity);
    }

    @Override
    public void createStudentFriendFeedback(String studentId, FriendFeedbackEntity friendFeedbackEntity) {
        StudentEntity studentEntity = getStudent(studentId);
        if (Boolean.TRUE.equals(studentEntity.getFriendFeedbackUploaded())) {
            throw new StudentFeedbackAlreadyExistsException(FRIEND_FEEDBACK_ALREADY_EXISTS_MESSAGE);
        }
        if (studentEntity.getFriendFeedback1() == null) {
            studentEntity.setFriendFeedback1(friendFeedbackEntity);
        } else if (studentEntity.getFriendFeedback2() == null) {
            studentEntity.setFriendFeedback2(friendFeedbackEntity);
            studentEntity.setFriendFeedbackUploaded(true);
        }
        studentRepository.save(studentEntity);
    }

    @Override
    public void deleteStudent(Long id) {
        StudentEntity student = getStudent(id);
        studentFilesService.deleteStudentFiles(id);
        studentRepository.deleteById(id);
        log.info("Student deleted: {}", student.getFullSlavicName());
    }

    @Override
    public boolean isStudentExists(Long id) {
        return studentRepository.findById(id).isPresent();
    }

}
