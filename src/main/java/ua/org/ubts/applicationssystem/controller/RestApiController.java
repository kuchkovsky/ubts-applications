package ua.org.ubts.applicationssystem.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ua.org.ubts.applicationssystem.entity.Student;
import ua.org.ubts.applicationssystem.service.StudentService;
import ua.org.ubts.applicationssystem.util.ResponseMessage;

import java.util.List;

/**
 * Created by Yaroslav on 14.07.2017.
 */

@RestController
@RequestMapping("/api")
public class RestApiController {

    private static final Logger logger = Logger.getLogger(RestApiController.class);

    @Autowired
    StudentService studentService;

    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.findAll();
        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @RequestMapping(value = "/students/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getStudent(@PathVariable("id") Long id) {
        Student student = studentService.findById(id);
        if (student == null) {
            return new ResponseEntity<>(new ResponseMessage("User not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @RequestMapping(value = "/students", method = RequestMethod.POST)
    public ResponseEntity<ResponseMessage> addStudent(@RequestBody Student student, UriComponentsBuilder ucBuilder) {
        System.out.println(student);
        if (studentService.isExist(student)) {
            String errorMessage = "Unable to create. A Student with name " + student.getFullSlavicName()
                    + " already exist";
            logger.info("DB operation error: " + errorMessage);
            return new ResponseEntity<>(new ResponseMessage(errorMessage), HttpStatus.CONFLICT);
        }
        studentService.save(student);
        logger.info("Added to DB: " + student);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/students/{id}").buildAndExpand(student.getId()).toUri());
        return new ResponseEntity<>(new ResponseMessage("OK"), headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/students/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseMessage> deleteStudent(@PathVariable("id") Long id) {
        Student student = studentService.findById(id);
        if (student == null) {
            String errorMessage = "Unable to delete. User with id " + id + " not found";
            logger.info("DB operation error: " + errorMessage);
            return new ResponseEntity<>(new ResponseMessage(errorMessage), HttpStatus.NOT_FOUND);
        }
        studentService.deleteById(id);
        logger.info("Deleted from DB: " + student);
        return new ResponseEntity<>(new ResponseMessage("OK"), HttpStatus.OK);
    }

    @RequestMapping(value = "/students/", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseMessage> deleteAllStudents() {
        studentService.deleteAll();
        logger.info("All students deleted from DB");
        return new ResponseEntity<>(new ResponseMessage("OK"), HttpStatus.OK);
    }

}
