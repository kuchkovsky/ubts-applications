package ua.org.ubts.applications.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.org.ubts.applications.model.StudentFilesUploadModel;
import ua.org.ubts.applications.service.StudentFilesService;

@RestController
@RequestMapping("/api/files/students")
public class StudentFilesApiController {

    @Autowired
    private StudentFilesService studentFilesService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadStudentFiles(@ModelAttribute StudentFilesUploadModel model) {
       studentFilesService.saveStudentFiles(model);
    }

    @RequestMapping(method = RequestMethod.HEAD)
    public void checkIfStudentFilesExists(@RequestParam("first_name") String firstName,
                                         @RequestParam("middle_name") String middleName,
                                         @RequestParam("last_name") String lastName) {
        studentFilesService.checkIfStudentFilesExists(firstName, middleName, lastName);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ByteArrayResource> downloadUserFiles(@PathVariable("id") Long id) {
        return studentFilesService.getStudentFiles(id);
    }

}
