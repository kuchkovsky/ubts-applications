package ua.org.ubts.applications.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.ubts.applications.entity.StudentEntity;
import ua.org.ubts.applications.exception.FileDeleteException;
import ua.org.ubts.applications.exception.StudentAlreadyExistsException;
import ua.org.ubts.applications.exception.StudentNotFoundException;
import ua.org.ubts.applications.repository.*;
import ua.org.ubts.applications.service.*;
import ua.org.ubts.applications.util.UserFilesManager;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class StudentServiceImpl implements StudentService {

    private static final String STUDENT_ID_NOT_FOUND_MESSAGE = "Could not find student with id=";
    private static final String STUDENT_NOT_FOUND_MESSAGE = "Could not find student with name='%s %s %s'";
    private static final String STUDENT_ALREADY_EXISTS_MESSAGE = "Student with name='%s %s %s' already exists";
    private static final String STUDENT_FILES_DELETE_ERROR_MESSAGE = "Could not delete files for student with id=";

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

    private void saveToDb(StudentEntity studentEntity) {
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
        studentRepository.save(studentEntity);
    }

    @Override
    public StudentEntity getStudent(Long id) {
        return studentRepository.findById(id).orElseThrow(() ->
                new StudentNotFoundException(STUDENT_ID_NOT_FOUND_MESSAGE + id));
    }

    @Override
    public StudentEntity getStudent(String firstName, String middleName, String lastName) {
        return studentRepository.findByFullName(firstName, middleName, lastName)
                .orElseThrow(() -> new StudentNotFoundException(String.format(STUDENT_NOT_FOUND_MESSAGE,
                        firstName, middleName, lastName)));
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
    public void createStudent(StudentEntity studentEntity) {
        studentRepository.findByFullName(studentEntity.getFirstName(), studentEntity.getMiddleName(),
                studentEntity.getLastName()).ifPresent(student -> {
            throw new StudentAlreadyExistsException(String.format(STUDENT_ALREADY_EXISTS_MESSAGE,
                    student.getFirstName(), student.getMiddleName(), student.getLastName()));
        });
        saveToDb(studentEntity);
    }

    @Override
    public void updateStudent(StudentEntity studentEntity) {
        studentRepository.save(studentEntity);
    }

    @Override
    public void deleteStudent(Long id) {
        StudentEntity student = getStudent(id);
        deleteStudentFiles(student);
        studentRepository.deleteById(id);
        log.info("Student deleted: {}", student.getFullSlavicName());
    }

    private void deleteStudentFiles(StudentEntity studentEntity) {
        if (Boolean.TRUE.equals(studentEntity.getFilesUploaded())) {
            try {
                UserFilesManager.deleteStudentFiles(studentEntity);
            } catch (IOException e) {
                log.error(STUDENT_FILES_DELETE_ERROR_MESSAGE + studentEntity.getId(), e);
                throw new FileDeleteException(STUDENT_FILES_DELETE_ERROR_MESSAGE + studentEntity.getId());
            }
        }
    }

    @Override
    public boolean isStudentExists(Long id) {
        return studentRepository.findById(id).isPresent();
    }

}
