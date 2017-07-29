package ua.org.ubts.applicationssystem.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.ubts.applicationssystem.entity.Education;
import ua.org.ubts.applicationssystem.entity.Program;
import ua.org.ubts.applicationssystem.entity.Student;
import ua.org.ubts.applicationssystem.repository.StudentRepository;
import ua.org.ubts.applicationssystem.service.*;
import ua.org.ubts.applicationssystem.util.UserFilesManager;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

/**
 * Created by Yaroslav on 18.07.2017.
 */

@Service("studentService")
@Transactional
public class StudentServiceImpl implements StudentService {

    private static final Logger logger = Logger.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProgramService programService;

    @Autowired
    private ResidenceService residenceService;

    @Autowired
    private EducationService educationService;

    @Autowired
    private MaritalDataService maritalDataService;

    @Autowired
    private ChurchDataService churchDataService;

    @Autowired
    private ChurchMinistryService churchMinistryService;

    @Autowired
    private HealthDataService healthDataService;

    @Override
    public Student findById(Long id) {
        return studentRepository.findOne(id);
    }

    @Override
    public Student findByName(String firstName, String middleName, String lastName) {
        return studentRepository.findByName(firstName, middleName, lastName);
    }

    @Override
    public Student findByData(Student student) {
        return findByName(student.getFirstName(), student.getMiddleName(), student.getLastName());
    }

    @Override
    public void save(Student student) {
        Program program = programService.findByData(student.getProgram());
        if (program != null) {
            student.setProgram(program);
        }
        residenceService.save(student.getResidence());
        Education education = educationService.findByData(student.getEducation());
        if (education != null) {
            student.setEducation(education);
        }
        maritalDataService.save(student.getMaritalData());
        if (student.getChurchData() != null && student.getChurchMinistry() != null) {
            churchDataService.save(student.getChurchData());
            churchMinistryService.save(student.getChurchMinistry());
        }
        healthDataService.save(student.getHealthData());
        studentRepository.save(student);
    }

    @Override
    public void deleteById(Long id) {
        deleteStudentFiles(findById(id));
        studentRepository.delete(id);
    }

    @Override
    public void deleteAll() {
        for (Student student : findAll()) {
            deleteStudentFiles(student);
        }
        studentRepository.deleteAll();
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public boolean isExist(Student student) {
        return findByData(student) != null;
    }

    private void deleteStudentFiles(Student student) {
        if (student != null && Boolean.TRUE.equals(student.hasFilesUploaded())) {
            try {
                UserFilesManager.deleteStudentFiles(student);
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }

}
