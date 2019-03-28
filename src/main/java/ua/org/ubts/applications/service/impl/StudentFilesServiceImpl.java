package ua.org.ubts.applications.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;
import ua.org.ubts.applications.dto.StudentFilesDto;
import ua.org.ubts.applications.dto.UploadedFileDto;
import ua.org.ubts.applications.entity.StudentEntity;
import ua.org.ubts.applications.exception.*;
import ua.org.ubts.applications.repository.StudentRepository;
import ua.org.ubts.applications.service.StudentFilesService;
import ua.org.ubts.applications.service.StudentService;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Slf4j
public class StudentFilesServiceImpl implements StudentFilesService {

    private static final String STUDENTS_DIRECTORY = "students";
    private static final int PHOTO_WIDTH = 400;
    private static final String WRITE_FILE_ERROR_MESSAGE = "Could not save file on server";
    private static final String DELETE_REQUEST_DESERIALIZATION_ERROR = "Could not deserialize delete request";
    private static final String TEMPORARY_DOCUMENT_DELETE_ERROR_MESSAGE = "Could not delete temporary document ";
    private static final String UNSUPPORTED_MIME_TYPES_ERROR_MESSAGE = "Unsupported media type";
    private static final String DOCUMENT_SAVE_ERROR_MESSAGE = "Could not save document for book with id=";
    private static final String EXTRACT_FIELD_ERROR_MESSAGE = "Could not extract field value";
    private static final String READ_STUDENT_FILES_ERROR_MESSAGE = "Could not read files for student with id=";
    private static final String STUDENT_FILES_DELETE_ERROR_MESSAGE = "Could not delete files for student with id=";

    private static final List<String> allowedMimeTypes = List.of(
            "image/jpeg",
            "image/pjpeg",
            "image/png",
            "application/pdf");

    @Autowired
    private String appDirectory;

    @Autowired
    private String tmpDirectory;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    private String getStudentDirectory(Long id) {
        return appDirectory + File.separator + STUDENTS_DIRECTORY + File.separator + id;
    }

    private String getFileExtensionFromName(String fileName) {
        return "." + FilenameUtils.getExtension(fileName);
    }

    private Path getPath(String temporaryFileName) {
        return Paths.get(tmpDirectory + File.separator + temporaryFileName);
    }

    private Path getPath(Long id, String fileName, String extension) {
        return Paths.get(getStudentDirectory(id) + File.separator + fileName + extension);
    }

    private void saveFile(Long studentId, String tmpFileName, String fileName) {
        String extension = getFileExtensionFromName(tmpFileName);
        Path pathFrom = getPath(tmpFileName);
        Path pathTo = getPath(studentId, fileName, extension);
        try {
            Files.createDirectories(Paths.get(getStudentDirectory(studentId)));
            Files.move(pathFrom, pathTo);
        } catch (IOException e) {
            log.error(DOCUMENT_SAVE_ERROR_MESSAGE + studentId, e);
            throw new FileDeleteException(DOCUMENT_SAVE_ERROR_MESSAGE + studentId);
        }
    }

    private String deriveStudentFilesName(StudentEntity studentEntity) {
        return studentEntity.getLastName().toLowerCase() + "_" + studentEntity.getFirstName().toLowerCase().substring(0, 1)
                + "_" + studentEntity.getMiddleName().toLowerCase().substring(0, 1);
    }

    private String getMimeTypeFromBytes(byte[] bytes) {
        InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(bytes));
        try {
            String mimeType = URLConnection.guessContentTypeFromStream(inputStream);
            inputStream.close();
            return mimeType;
        } catch (IOException e) {
            throw new RuntimeException("Failed to decode MimeType from bytes");
        }
    }

    private String getFileExtensionFromMimeType(String mimeType) {
        MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
        try {
            MimeType type = allTypes.forName(mimeType);
            return type.getExtension();
        } catch (MimeTypeException e) {
            throw new RuntimeException("Failed to get extension from mimeType");
        }
    }


    private byte[] resizeImageToWidth(byte[] imageBytes, int width) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        BufferedImage bufferedImage = ImageIO.read(bis);
        bis.close();
        int resizedImageHeight = (int) (bufferedImage.getHeight() / ((double) bufferedImage.getWidth() / width));
        Image resizedImage = bufferedImage.getScaledInstance(width, resizedImageHeight, Image.SCALE_SMOOTH);
        BufferedImage resizedBufferedImage = new BufferedImage(width, resizedImageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resizedBufferedImage.createGraphics();
        g2d.drawImage(resizedImage, 0, 0, null);
        g2d.dispose();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String imageType = getFileExtensionFromMimeType(getMimeTypeFromBytes(imageBytes)).substring(1);
        ImageIO.write(resizedBufferedImage, imageType, baos);
        baos.flush();
        byte[] resizedImageBytes = baos.toByteArray();
        baos.close();
        return resizedImageBytes;
    }

    @Override
    public UploadedFileDto saveTemporaryDocument(MultipartFile document) {
        if (!allowedMimeTypes.contains(document.getContentType())) {
            throw new UnsupportedMimeTypesException(UNSUPPORTED_MIME_TYPES_ERROR_MESSAGE);
        }
        String fileName = UUID.randomUUID().toString() + getFileExtensionFromName(document.getOriginalFilename());
        String path = tmpDirectory + File.separator + fileName;
        try {
            document.transferTo(Paths.get(path));
        } catch (IOException e) {
            log.error(WRITE_FILE_ERROR_MESSAGE, e);
            throw new FileWriteException(WRITE_FILE_ERROR_MESSAGE);
        }
        return new UploadedFileDto(fileName);
    }

    @Override
    public void deleteTemporaryDocument(HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        UploadedFileDto document;
        try {
            document = mapper.readValue(request.getInputStream(), UploadedFileDto.class);
        } catch (IOException e) {
            log.error(DELETE_REQUEST_DESERIALIZATION_ERROR);
            throw new ServiceException(DELETE_REQUEST_DESERIALIZATION_ERROR);
        }
        String fileName = document.getFileName();
        try {
            Files.delete(getPath(fileName));
        } catch (IOException e) {
            log.error(TEMPORARY_DOCUMENT_DELETE_ERROR_MESSAGE + fileName, e);
            throw new FileDeleteException(TEMPORARY_DOCUMENT_DELETE_ERROR_MESSAGE + fileName);
        }
    }

    @Override
    public void saveStudentFiles(Long studentId, StudentFilesDto studentFilesDto) {
        Field[] fields = studentFilesDto.getClass().getDeclaredFields();
        Arrays.stream(fields).forEach(field -> {
            field.setAccessible(true);
            try {
                String fieldName = field.getName();
                String fieldValue = (String) field.get(studentFilesDto);
                saveFile(studentId, fieldValue, fieldName);
            } catch (IllegalAccessException e) {
                log.error(EXTRACT_FIELD_ERROR_MESSAGE, e);
                throw new ServiceException(EXTRACT_FIELD_ERROR_MESSAGE);
            }
        });
        StudentEntity studentEntity = studentService.getStudent(studentId);
        studentRepository.save(studentEntity);
    }

    @Override
    public ResponseEntity<ByteArrayResource> getStudentFiles(Long id) {
        StudentEntity studentEntity = studentService.getStudent(id);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (Stream<Path> paths = Files.walk(Paths.get(getStudentDirectory(id)))) {
                ZipOutputStream zos = new ZipOutputStream(baos);
                paths.filter(Files::isRegularFile).forEach(path -> {
                    try {
                        zos.putNextEntry(new ZipEntry(path.getFileName().toString()));
                        byte[] bytes = Files.readAllBytes(path);
                        zos.write(bytes, 0, bytes.length);
                        zos.closeEntry();
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                });
                zos.close();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
            byte[] zip = baos.toByteArray();
            String fileName = deriveStudentFilesName(studentEntity) + ".zip";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment;filename=" + UriUtils.encodePath(fileName, "UTF-8"))
                    .contentType(MediaType.parseMediaType("application/zip")).contentLength(zip.length)
                    .body(new ByteArrayResource(zip));
        } catch (UncheckedIOException e) {
            log.error(READ_STUDENT_FILES_ERROR_MESSAGE + id, e);
            throw new FileReadException(READ_STUDENT_FILES_ERROR_MESSAGE + id);
        }
    }

    @Override
    public ResponseEntity<ByteArrayResource> getStudentPhoto(Long id) {
        File dir = new File(getStudentDirectory(id));
        File[] photoFiles = dir.listFiles((dir1, name) -> name.startsWith("photo"));
        try {
            if (photoFiles == null || photoFiles[0] == null) {
                throw new FileReadException(READ_STUDENT_FILES_ERROR_MESSAGE + id);
            }
            File photo = photoFiles[0];
            byte[] resizedPhotoBytes = resizeImageToWidth(Files.readAllBytes(photo.toPath()), PHOTO_WIDTH);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "inline;filename=" + UriUtils.encodePath(photo.getName(), "UTF-8"))
                    .contentType(MediaType.parseMediaType(getMimeTypeFromBytes(resizedPhotoBytes)))
                    .contentLength(resizedPhotoBytes.length)
                    .body(new ByteArrayResource(resizedPhotoBytes));
        } catch (IOException e) {
            log.error(READ_STUDENT_FILES_ERROR_MESSAGE + id, e);
            throw new FileReadException(READ_STUDENT_FILES_ERROR_MESSAGE + id);
        }
    }

    @Override
    public void deleteStudentFiles(Long id) {
        try {
            FileUtils.deleteDirectory(new File(getStudentDirectory(id)));
        } catch (IOException e) {
            log.error(STUDENT_FILES_DELETE_ERROR_MESSAGE + id, e);
            throw new FileDeleteException(STUDENT_FILES_DELETE_ERROR_MESSAGE + id);
        }
    }

}
