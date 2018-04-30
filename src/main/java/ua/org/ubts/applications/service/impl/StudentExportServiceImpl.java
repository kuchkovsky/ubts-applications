package ua.org.ubts.applications.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.ubts.applications.entity.StudentEntity;
import ua.org.ubts.applications.exception.ExportException;
import ua.org.ubts.applications.service.StudentExportService;
import ua.org.ubts.applications.service.StudentService;
import ua.org.ubts.applications.util.ConfigManager;
import ua.org.ubts.applications.util.DavManager;
import ua.org.ubts.applications.util.XlsxExporter;

import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StudentExportServiceImpl implements StudentExportService {

    private static final String STUDENT_EXPORT_TO_CLOUD_ERROR_MESSAGE = "Could not export user to cloud";

    @Autowired
    private StudentService studentService;

    @Override
    public void exportStudentToCloud(Long id) {
        StudentEntity studentEntity = studentService.getStudent(id);
        try {
            ConfigManager.DavProperties davProperties = ConfigManager.getDavProperties();
            DavManager davManager = new DavManager(davProperties.getLogin(), davProperties.getPassword(),
                    davProperties.getUrl());
            davManager.exportStudent(studentEntity);
        } catch (IOException | InterruptedException e) {
            log.error(STUDENT_EXPORT_TO_CLOUD_ERROR_MESSAGE, e);
            throw new ExportException(STUDENT_EXPORT_TO_CLOUD_ERROR_MESSAGE);
        }
    }

    @Override
    public void exportAllStudentsToCloud() {
        List<StudentEntity> students = studentService.getStudents();
        try {
            ConfigManager.DavProperties davProperties = ConfigManager.getDavProperties();
            DavManager davManager = new DavManager(davProperties.getLogin(), davProperties.getPassword(),
                    davProperties.getUrl());
            for (StudentEntity student : students) {
                davManager.exportStudent(student);
            }
        } catch (IOException | InterruptedException e) {
            log.error(STUDENT_EXPORT_TO_CLOUD_ERROR_MESSAGE, e);
            throw new ExportException(STUDENT_EXPORT_TO_CLOUD_ERROR_MESSAGE);
        }
    }

    @Override
    public void exportAllStudentsToExcel() {
        Integer currentYear = Year.now().getValue();
        List<Integer> years = new ArrayList<>();
        years.add(currentYear);
        List<StudentEntity> studentList = studentService.getStudents(Optional.of(years));
        XlsxExporter xlsxExporter = new XlsxExporter();
        try {
            ConfigManager.DavProperties davProperties = ConfigManager.getDavProperties();
            DavManager davManager = new DavManager(davProperties.getLogin(), davProperties.getPassword(),
                    davProperties.getUrl());
            String folder = "/ApplicationSystem/Excel/";
            if (!davManager.exists(folder)) {
                davManager.createDirectoryRecursive(folder);
            }
            log.info("Putting xlsx file to cloud...");
            davManager.put(xlsxExporter.generateXlsx(studentList), folder + currentYear.toString() + ".xlsx");
        } catch (IOException e) {
            log.error(STUDENT_EXPORT_TO_CLOUD_ERROR_MESSAGE, e);
            throw new ExportException(STUDENT_EXPORT_TO_CLOUD_ERROR_MESSAGE);
        }
    }

}
