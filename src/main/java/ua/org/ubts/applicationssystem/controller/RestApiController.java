package ua.org.ubts.applicationssystem.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ua.org.ubts.applicationssystem.dto.StudentListItem;
import ua.org.ubts.applicationssystem.entity.*;
import ua.org.ubts.applicationssystem.model.StudentFilesUploadModel;
import ua.org.ubts.applicationssystem.service.ProgramService;
import ua.org.ubts.applicationssystem.service.StudentService;
import ua.org.ubts.applicationssystem.util.DavManager;
import ua.org.ubts.applicationssystem.util.Importer;
import ua.org.ubts.applicationssystem.util.ResponseMessage;
import ua.org.ubts.applicationssystem.util.UserFilesManager;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yaroslav on 14.07.2017.
 */

@RestController
@RequestMapping("/api")
public class RestApiController {

    private static final Logger logger = Logger.getLogger(RestApiController.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private ProgramService programService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.findAll();
        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/students/list")
    public ResponseEntity<List<StudentListItem>> getStudentList() {
        List<Student> students = studentService.findAll();
        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<StudentListItem> studentList = new ArrayList<>();
        students.forEach(student -> {
            studentList.add(new StudentListItem(student.getId(), student.getFullSlavicName(), student.getProgram()));
        });
        return new ResponseEntity<List<StudentListItem>>(studentList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
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

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/students")
    public ResponseEntity<ResponseMessage> deleteAllStudents() {
        studentService.deleteAll();
        logger.info("All students deleted from DB");
        return new ResponseEntity<>(new ResponseMessage("OK"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/programs")
    public ResponseEntity<List<Program>> getPrograms() {
        List<Program> programs = programService.findAll();
        if (programs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(programs, HttpStatus.OK);
    }

    @PostMapping("/students/files")
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
        logger.info("Saving uploaded files... User: " + model.getLastName() + " " + model.getFirstName() + " "
                + model.getMiddleName());
        try {
            UserFilesManager.saveStudentFiles(model);
            student.setFilesUploaded(true);
            studentService.save(student);
            logger.info("Files were saved successfully. User: " + model.getLastName() + " " + model.getFirstName() + " "
                    + model.getMiddleName());
        } catch (IOException e) {
            logger.error(e);
            return new ResponseEntity<>(new ResponseMessage("Files not saved"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseMessage("Successfully uploaded"), HttpStatus.OK);
    }

    @RequestMapping(value = "/students/files/exist", method = RequestMethod.HEAD)
    public ResponseEntity checkIfStudentFilesExist(@RequestParam("first_name") String firstName,
                                                   @RequestParam("middle_name") String middleName,
                                                   @RequestParam("last_name") String lastName) {
        Student student = studentService.findByName(firstName, middleName, lastName);
        if (student != null && Boolean.TRUE.equals(student.hasFilesUploaded())) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/students/files/{id}")
    public void getUserFiles(@PathVariable("id") Integer id, HttpServletResponse response) {
        Student student = studentService.findById(id);
        if (student == null) {
            try {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            } catch (IOException e) {
                logger.error(e);
            }
            return;
        }
        try {
            ByteArrayOutputStream outputStream = UserFilesManager.getStudentFiles(student);
            String filename = UserFilesManager.getStudentDirectory(student) + ".zip";
            filename = URLEncoder.encode(filename,"UTF-8");
            response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\"");
            response.setContentType("application/zip");
            response.getOutputStream().write(outputStream.toByteArray());
            response.flushBuffer();
        } catch (IOException e) {
            logger.error(e);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/import")
    public ResponseEntity<ResponseMessage> importStudentsFromOldDataBase() {
        Importer.importStudents(studentService);
        return new ResponseEntity<>(new ResponseMessage("OK"), HttpStatus.OK);
    }

    @GetMapping("/student/{id}/export/cloud")
    public ResponseEntity<ResponseMessage> exportToCloud(@PathVariable("id") Integer id) {
        DavManager davManager = new DavManager("LOGIN","PASSWORD",
                "https://cloud.ubts.org.ua/remote.php/webdav");
        Student student = studentService.findById(id);
        logger.info("Exporting student to cloud: " + student.getFullSlavicName());
        davManager.exportStudent(student);
        return new ResponseEntity<>(new ResponseMessage("OK"), HttpStatus.OK);
    }

    @GetMapping("/student/export/cloud")
    public ResponseEntity<ResponseMessage> exportAllToCloud() {
        DavManager davManager = new DavManager("LOGIN", "PASSWORD",
                "https://cloud.ubts.org.ua/remote.php/webdav");
        List<Student> studentList = studentService.findAll();
        for (Student student : studentList) {
            logger.info("Exporting student to cloud: " + student.getFullSlavicName());
            davManager.exportStudent(student);
        }
        return new ResponseEntity<>(new ResponseMessage("OK"), HttpStatus.OK);
    }
}
