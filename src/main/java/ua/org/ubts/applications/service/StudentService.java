package ua.org.ubts.applications.service;


import ua.org.ubts.applications.entity.StudentEntity;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    StudentEntity getStudent(Long id);

    StudentEntity getStudent(String firstName, String middleName, String lastName);

    List<StudentEntity> getStudents();

    List<StudentEntity> getStudents(Optional<List<Integer>> years);

    void createStudent(StudentEntity studentEntity);

    void updateStudent(StudentEntity studentEntity);

    void deleteStudent(Long id);

    boolean isStudentExists(Long id);

}
