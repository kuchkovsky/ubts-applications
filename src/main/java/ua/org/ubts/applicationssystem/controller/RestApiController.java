package ua.org.ubts.applicationssystem.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ua.org.ubts.applicationssystem.entity.Student;
import ua.org.ubts.applicationssystem.model.StudentFilesUploadModel;
import ua.org.ubts.applicationssystem.service.StudentService;
import ua.org.ubts.applicationssystem.util.ResponseMessage;
import ua.org.ubts.applicationssystem.util.UserFilesManager;

import java.io.IOException;
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

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.findAll();
        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<?> getStudent(@PathVariable("id") Integer id) {
        Student student = studentService.findById(id);
        if (student == null) {
            return new ResponseEntity<>(new ResponseMessage("User not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping("/students")
    public ResponseEntity<ResponseMessage> addStudent(@RequestBody Student student, UriComponentsBuilder ucBuilder) {
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

    @DeleteMapping("/students/{id}")
    public ResponseEntity<ResponseMessage> deleteStudent(@PathVariable("id") Integer id) {
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

    @DeleteMapping("/students")
    public ResponseEntity<ResponseMessage> deleteAllStudents() {
        studentService.deleteAll();
        logger.info("All students deleted from DB");
        return new ResponseEntity<>(new ResponseMessage("OK"), HttpStatus.OK);
    }

    @PostMapping("/student/files")
    public ResponseEntity<ResponseMessage> uploadStudentFiles(@ModelAttribute StudentFilesUploadModel model) {
        Student student = studentService.findByName(model.getFirstName(), model.getMiddleName(), model.getLastName());
        if (student == null) {
            return new ResponseEntity<>(new ResponseMessage("Student not found in database"), HttpStatus.NOT_FOUND);
        }
        if (Boolean.TRUE.equals(student.hasFilesUploaded())) {
            return new ResponseEntity<>(new ResponseMessage("Student files already uploaded"), HttpStatus.CONFLICT);
        }
        if (!model.areMimeTypesCorrect()) {
            return new ResponseEntity<>(new ResponseMessage("Unsupported media type"),
                    HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
        logger.debug("Files upload for " + model.getLastName() + " " + model.getFirstName() + " "
                + model.getMiddleName());
        try {
            UserFilesManager.saveStudentFiles(model);
            student.setFilesUploaded(true);
            studentService.save(student);
        } catch (IOException e) {
            logger.error(e);
            return new ResponseEntity<>(new ResponseMessage("Files not saved"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseMessage("Successfully uploaded"), HttpStatus.OK);
    }

    @RequestMapping(value = "/student/files/exist", method = RequestMethod.HEAD)
    public ResponseEntity checkIfStudentFilesExist(@RequestParam("first_name") String firstName,
                                                   @RequestParam("middle_name") String middleName,
                                                   @RequestParam("last_name") String lastName) {
        Student student = studentService.findByName(firstName, middleName, lastName);
        if (student != null && Boolean.TRUE.equals(student.hasFilesUploaded())) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}
