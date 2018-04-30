package ua.org.ubts.applications.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;
import ua.org.ubts.applications.entity.StudentEntity;
import ua.org.ubts.applications.exception.*;
import ua.org.ubts.applications.model.StudentFilesUploadModel;
import ua.org.ubts.applications.service.StudentFilesService;
import ua.org.ubts.applications.service.StudentService;
import ua.org.ubts.applications.util.UserFilesManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

@Service
@Slf4j
public class StudentFilesServiceImpl implements StudentFilesService {

    private static final String STUDENT_FILES_ALREADY_UPLOADED_ERROR_MESSAGE
            = "Files already uploaded for student with name='%s %s %s'";
    private static final String UNSUPPORTED_MIME_TYPES_ERROR_MESSAGE = "Unsupported media type";
    private static final String SAVING_STUDENT_FILES_MESSAGE = "Saving uploaded files for student with name='%s %s %s'";
    private static final String FILES_SAVED_SUCCESS_MESSAGE
            = "Files were saved successfully for student with name='%s %s %s'";
    private static final String FILES_SAVE_ERROR_MESSAGE = "Could not save files for student with name='%s %s %s'";
    private static final String STUDENT_FILES_NOT_FOUND_ERROR_MESSAGE
            = "Could not find files for student with name='%s %s %s'";
    private static final String READ_STUDENT_FILES_ERROR_MESSAGE
            = "Could not read files for student with name='%s %s %s'";

    @Autowired
    private StudentService studentService;


    @Override
    public void saveStudentFiles(StudentFilesUploadModel model) {
        StudentEntity studentEntity = studentService.getStudent(model.getFirstName(), model.getMiddleName(),
                model.getLastName());
        checkUploadedFiles(studentEntity);
        checkMimeTypes(model);
        log.info(String.format(SAVING_STUDENT_FILES_MESSAGE,
                studentEntity.getLastName(), studentEntity.getFirstName(), studentEntity.getMiddleName()));
        saveStudentFiles(model, studentEntity);
    }

    @Override
    public void checkIfStudentFilesExists(String firstName, String middleName, String lastName) {
        Boolean filesExists = studentService.getStudent(firstName, middleName, lastName).getFilesUploaded();
        if (!Boolean.TRUE.equals(filesExists)) {
            throw new FilesNotFoundException(String.format(STUDENT_FILES_NOT_FOUND_ERROR_MESSAGE,
                    lastName, firstName, middleName));
        }
    }

    @Override
    public ResponseEntity<ByteArrayResource> getStudentFiles(Long id) {
        StudentEntity studentEntity = studentService.getStudent(id);
        try {
            ByteArrayOutputStream outputStream = UserFilesManager.getStudentFiles(studentEntity);
            String fileName = UserFilesManager.getStudentDirectory(studentEntity) + ".zip";
            byte[] zip = outputStream.toByteArray();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment;filename=" + UriUtils.encodePath(fileName, "UTF-8"))
                    .contentType(MediaType.parseMediaType("application/zip")).contentLength(zip.length)
                    .body(new ByteArrayResource(zip));
        } catch (UncheckedIOException e) {
            String errorText = String.format(READ_STUDENT_FILES_ERROR_MESSAGE,
                    studentEntity.getLastName(), studentEntity.getFirstName(), studentEntity.getMiddleName());
            log.error(errorText, e);
            throw new FileReadException(errorText);
        }
    }

    private void checkUploadedFiles(StudentEntity studentEntity) {
        if (Boolean.TRUE.equals(studentEntity.getFilesUploaded())) {
            throw new FilesAlreadyUploadedException(String.format(STUDENT_FILES_ALREADY_UPLOADED_ERROR_MESSAGE,
                    studentEntity.getLastName(), studentEntity.getFirstName(), studentEntity.getMiddleName()));
        }
    }

    private void checkMimeTypes(StudentFilesUploadModel model) {
        if (!model.areMimeTypesCorrect()) {
            throw new UnsupportedMimeTypesException(UNSUPPORTED_MIME_TYPES_ERROR_MESSAGE);
        }
    }

    private void saveStudentFiles(StudentFilesUploadModel model, StudentEntity studentEntity) {
        try {
            UserFilesManager.saveStudentFiles(model);
            studentEntity.setFilesUploaded(true);
            studentService.updateStudent(studentEntity);
            log.info(String.format(FILES_SAVED_SUCCESS_MESSAGE,
                    studentEntity.getLastName(), studentEntity.getFirstName(), studentEntity.getMiddleName()));
        } catch (IOException e) {
            String errorText = String.format(FILES_SAVE_ERROR_MESSAGE,
                    studentEntity.getLastName(), studentEntity.getFirstName(), studentEntity.getMiddleName());
            log.error(errorText, e);
            throw new FileOperationException(errorText);
        }
    }

}
