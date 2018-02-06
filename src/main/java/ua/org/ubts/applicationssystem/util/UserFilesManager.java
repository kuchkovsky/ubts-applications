package ua.org.ubts.applicationssystem.util;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import ua.org.ubts.applicationssystem.entity.Student;
import ua.org.ubts.applicationssystem.model.StudentFilesUploadModel;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class UserFilesManager {

    private static final Logger logger = Logger.getLogger(UserFilesManager.class);

    private static final String APP_FOLDER = System.getProperty("user.home") + File.separator + "UBTS ApplSystem"
            + File.separator;

    static {
        try {
            createDirectoryIfNotExist(APP_FOLDER);
            createDirectoryIfNotExist(APP_FOLDER + "students");
        } catch (IOException e) {
            logger.error(e);
        }
    }

    private UserFilesManager() {}

    public static void saveStudentFiles(StudentFilesUploadModel model) throws IOException {
        String dir = "students" + File.separator + getStudentDirectory(model) + File.separator;
        createDirectoryIfNotExist(APP_FOLDER + dir);
        saveFile(model.getPhoto(), dir  + "photo." + getFileExtension(model.getPhoto()));
        saveFile(model.getPassport1(), dir + "passport1." + getFileExtension(model.getPassport1()));
        saveFile(model.getPassport2(), dir  + "passport2." + getFileExtension(model.getPassport2()));
        saveFile(model.getPassport3(), dir + "passport3." + getFileExtension(model.getPassport3()));
        saveFile(model.getIdNumber(), dir + "id_number." + getFileExtension(model.getIdNumber()));
        saveFile(model.getDiploma1(), dir  + "diploma1." + getFileExtension(model.getDiploma1()));
        saveFile(model.getDiploma2(), dir + "diploma2." + getFileExtension(model.getDiploma2()));
        saveFile(model.getMedicalReference(), dir + "medical_reference."
                + getFileExtension(model.getMedicalReference()));
    }

    public static void deleteStudentFiles(Student student) throws IOException {
        String dir = "students" + File.separator + getStudentDirectory(student);
        FileUtils.deleteDirectory(new File(APP_FOLDER + dir));
    }

    private static void createDirectoryIfNotExist(String directoryPath) throws IOException {
        Path path = Paths.get(directoryPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    private static void saveFile(MultipartFile file, String relativePath) throws IOException {
        byte[] bytes = file.getBytes();
        Path path = Paths.get(APP_FOLDER + relativePath);
        Files.write(path, bytes);
    }

    private static String getStudentDirectory(StudentFilesUploadModel model) {
        return model.getLastName().toLowerCase() + "_" + model.getFirstName().toLowerCase().substring(0, 1)
                + "_" + model.getMiddleName().toLowerCase().substring(0, 1);
    }

    public static String getStudentDirectory(Student student) {
        return student.getLastName().toLowerCase() + "_" + student.getFirstName().toLowerCase().substring(0, 1)
                + "_" + student.getMiddleName().toLowerCase().substring(0, 1);
    }

    private static String getFileExtension(MultipartFile file) {
        return file.getOriginalFilename().split("\\.")[1].toLowerCase();
    }

    public static ByteArrayOutputStream getStudentFiles(Student student) {
        String dir = APP_FOLDER + "students" + File.separator + getStudentDirectory(student);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (Stream<Path> paths = Files.walk(Paths.get(dir))) {
            ZipOutputStream zos = new ZipOutputStream(baos);
            paths.filter(Files::isRegularFile).forEach(path -> {
                try {
                    zos.putNextEntry(new ZipEntry(path.getFileName().toString()));
                    byte[] bytes = Files.readAllBytes(path);
                    zos.write(bytes, 0, bytes.length);
                    zos.closeEntry();
                } catch (IOException e) {
                    logger.error(e);
                }
            });
            zos.close();
        } catch (Exception e) {
            logger.error(e);
        }
        return baos;
    }

    public static String getAppFolder() {
        return APP_FOLDER;
    }
}
