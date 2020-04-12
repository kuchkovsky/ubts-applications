package ua.org.ubts.applications.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.org.ubts.applications.converter.FriendFeedbackConverter;
import ua.org.ubts.applications.converter.PastorFeedbackConverter;
import ua.org.ubts.applications.converter.StudentConverter;
import ua.org.ubts.applications.dto.*;
import ua.org.ubts.applications.service.QuestionService;
import ua.org.ubts.applications.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private StudentConverter studentConverter;

    @Autowired
    private PastorFeedbackConverter pastorFeedbackConverter;

    @Autowired
    private FriendFeedbackConverter friendFeedbackConverter;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<StudentListItemDto> getStudentsList() {
        return studentConverter.convertToListDto(studentService.getStudents());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UuidDto createStudent(@RequestBody StudentDto studentDto) {
        return studentService.createStudent(studentDto);
    }

    @GetMapping("/{id}")
    public StudentDto getStudent(@PathVariable("id") Long id) {
        return studentConverter.convertToDto(studentService.getStudent(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
    }

    @PostMapping("{uuid}/pastor-feedback")
    @ResponseStatus(HttpStatus.CREATED)
    public void createStudentPastorFeedback(@PathVariable("uuid") String studentId,
                                               @RequestBody PastorFeedbackDto pastorFeedbackDto) {
        studentService.createStudentPastorFeedback(studentId, pastorFeedbackConverter.convertToEntity(pastorFeedbackDto));
    }

    @PostMapping("{uuid}/friend-feedback")
    @ResponseStatus(HttpStatus.CREATED)
    public void createStudentFriendFeedback(@PathVariable("uuid") String studentId,
                                            @RequestBody FriendFeedbackDto friendFeedbackDto) {
        studentService.createStudentFriendFeedback(studentId, friendFeedbackConverter.convertToEntity(friendFeedbackDto));
    }

    @GetMapping("{uuid}/full-name")
    public StudentFullNameDto getStudentFullName(@PathVariable("uuid") String studentId) {
        return studentService.getStudentFullName(studentId);
    }
}
