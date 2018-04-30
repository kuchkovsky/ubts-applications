package ua.org.ubts.applications.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import ua.org.ubts.applications.model.StudentFilesUploadModel;

public interface StudentFilesService {

    void saveStudentFiles(StudentFilesUploadModel model);

    void checkIfStudentFilesExists(String firstName, String middleName, String lastName);

    ResponseEntity<ByteArrayResource> getStudentFiles(Long id);

}
