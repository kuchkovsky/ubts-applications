package ua.org.ubts.applicationssystem.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.org.ubts.applicationssystem.entity.Student;
import ua.org.ubts.applicationssystem.model.StudentFilesUploadModel;
import ua.org.ubts.applicationssystem.service.StudentService;
import ua.org.ubts.applicationssystem.util.ResponseMessage;
import ua.org.ubts.applicationssystem.util.UserFilesManager;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class RestUploadController {

    private static final Logger logger = Logger.getLogger(RestApiController.class);

    @Autowired
    private StudentService studentService;

    @PostMapping("/student")
    public ResponseEntity<ResponseMessage> uploadStudentFiles(@ModelAttribute StudentFilesUploadModel model) {
        Student student = studentService.findByName(model.getFirstName(), model.getMiddleName(), model.getLastName());
        if (student == null) {
            return new ResponseEntity<>(new ResponseMessage("Student not found in database"), HttpStatus.NOT_FOUND);
        }
        if (Boolean.TRUE.equals(student.getFilesUploaded())) {
            return new ResponseEntity<>(new ResponseMessage("Student files already uploaded"), HttpStatus.CONFLICT);
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

}
