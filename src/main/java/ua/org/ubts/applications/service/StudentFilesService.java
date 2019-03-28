package ua.org.ubts.applications.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ua.org.ubts.applications.dto.StudentFilesDto;
import ua.org.ubts.applications.dto.UploadedFileDto;

import javax.servlet.http.HttpServletRequest;

public interface StudentFilesService {

    UploadedFileDto saveTemporaryDocument(MultipartFile document);

    void deleteTemporaryDocument(HttpServletRequest request);

    void saveStudentFiles(Long studentId, StudentFilesDto studentFilesDto);

    void deleteStudentFiles(Long id);

    ResponseEntity<ByteArrayResource> getStudentFiles(Long id);

    ResponseEntity<ByteArrayResource> getStudentPhoto(Long id);

}
