package ua.org.ubts.applications.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Data
public class StudentFilesUploadModel {

    private String firstName;
    private String middleName;
    private String lastName;
    private MultipartFile photo;
    private MultipartFile passport1;
    private MultipartFile passport2;
    private MultipartFile passport3;
    private MultipartFile idNumber;
    private MultipartFile diploma1;
    private MultipartFile diploma2;
    private MultipartFile medicalReference;

    public boolean areMimeTypesCorrect() {
        Field[] fields = getClass().getDeclaredFields();
        List<String> mimeTypes = Arrays.asList("image/jpeg", "image/pjpeg", "image/png", "application/pdf");
        return Arrays.stream(fields)
                .filter(field -> field.getType().equals(MultipartFile.class))
                .map(this::getMultipartFileFromField)
                .map(MultipartFile::getContentType)
                .anyMatch(mimeTypes::contains);
    }

    private MultipartFile getMultipartFileFromField(Field field) {
        try {
            return (MultipartFile) field.get(this);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to get class field via reflection");
        }
    }

}
