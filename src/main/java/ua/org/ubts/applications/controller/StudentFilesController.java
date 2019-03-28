package ua.org.ubts.applications.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.org.ubts.applications.dto.UploadedFileDto;
import ua.org.ubts.applications.service.StudentFilesService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/files/students")
public class StudentFilesController {

    @Autowired
    private StudentFilesService studentFilesService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UploadedFileDto uploadTemporaryDocument(@RequestParam("filepond") MultipartFile document) {
        return studentFilesService.saveTemporaryDocument(document);
    }

    @DeleteMapping
    public void deleteTemporaryDocument(HttpServletRequest request) {
        studentFilesService.deleteTemporaryDocument(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ByteArrayResource> downloadUserFiles(@PathVariable("id") Long id) {
        return studentFilesService.getStudentFiles(id);
    }

    @GetMapping("/photo/{id}")
    public ResponseEntity<ByteArrayResource> downloadUserPhoto(@PathVariable("id") Long id) {
        return studentFilesService.getStudentPhoto(id);
    }

}
