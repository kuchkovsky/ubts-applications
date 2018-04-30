package ua.org.ubts.applications.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.org.ubts.applications.converter.StudentConverter;
import ua.org.ubts.applications.dto.StudentDto;
import ua.org.ubts.applications.dto.StudentListItemDto;
import ua.org.ubts.applications.service.StudentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentApiController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentConverter studentConverter;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<StudentDto> getStudents(@RequestParam("year") Optional<List<Integer>> years) {
        return studentConverter.convertToDto(studentService.getStudents(years));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createStudent(@RequestBody StudentDto studentDto) {
        studentService.createStudent(studentConverter.convertToEntity(studentDto));
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

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/list")
    public List<StudentListItemDto> getStudentsList(@RequestParam("year") Optional<List<Integer>> years) {
        return studentConverter.convertToListDto(studentService.getStudents(years));
    }

}
