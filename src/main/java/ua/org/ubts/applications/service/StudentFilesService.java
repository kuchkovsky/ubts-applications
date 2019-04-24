package ua.org.ubts.applications.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ua.org.ubts.applications.dto.StudentFilesDto;
import ua.org.ubts.applications.dto.UploadedFileDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface StudentFilesService {

    UploadedFileDto saveTemporaryDocument(MultipartFile document);

    void deleteTemporaryDocument(HttpServletRequest request);

    void saveStudentFiles(Long studentId, StudentFilesDto studentFilesDto);

    void deleteStudentFiles(Long id);

    ResponseEntity<Resource> getStudentFiles(Long id);

    ResponseEntity<Resource> getStudentFile(Long id, String fileName);

    ResponseEntity<Resource> getStudentPhoto(Long id);

    List<String> listStudentFiles(Long id);

}
