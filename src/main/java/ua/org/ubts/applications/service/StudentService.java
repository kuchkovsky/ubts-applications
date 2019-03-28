package ua.org.ubts.applications.service;


import ua.org.ubts.applications.dto.StudentDto;
import ua.org.ubts.applications.dto.StudentFullNameDto;
import ua.org.ubts.applications.dto.UuidDto;
import ua.org.ubts.applications.entity.FriendFeedbackEntity;
import ua.org.ubts.applications.entity.PastorFeedbackEntity;
import ua.org.ubts.applications.entity.StudentEntity;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    StudentEntity getStudent(Long id);

    StudentEntity getStudent(String uuid);

    StudentFullNameDto getStudentFullName(String uuid);

    List<StudentEntity> getStudents();

    List<StudentEntity> getStudents(Optional<List<Integer>> years);

    UuidDto createStudent(StudentDto studentDto);

    void createStudentPastorFeedback(String studentId, PastorFeedbackEntity pastorFeedbackEntity);

    void createStudentFriendFeedback(String studentId, FriendFeedbackEntity friendFeedbackEntity);

    void deleteStudent(Long id);

    boolean isStudentExists(Long id);

}
