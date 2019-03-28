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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StudentExportServiceImpl implements StudentExportService {

    private static final String STUDENT_EXPORT_TO_CLOUD_ERROR_MESSAGE = "Could not export user to cloud";

    @Autowired
    private StudentService studentService;

    @Override
    public void exportStudentsToExcel(Optional<List<Integer>> years) {
        List<Integer> yearsList;
        XlsxExporter xlsxExporter = new XlsxExporter();
        if (years.isPresent()) {
            yearsList = years.get();
        } else {
            yearsList = new LinkedList<>();
            yearsList.add(Year.now().getValue());
        }
        try {
            ConfigManager.DavProperties davProperties = ConfigManager.getDavProperties();
            DavManager davManager = new DavManager(davProperties.getLogin(), davProperties.getPassword(),
                    davProperties.getUrl());
            String folder = "/ApplicationSystem/Excel/";
            if (!davManager.exists(folder)) {
                davManager.createDirectoryRecursive(folder);
            }
            List<StudentEntity> studentList;
            for (Integer year : yearsList) {
                List<Integer> currentYear = new LinkedList<>();
                currentYear.add(year);
                studentList = studentService.getStudents(Optional.of(currentYear));
                log.info("Putting xlsx file to cloud...");
                davManager.put(xlsxExporter.generateXlsx(studentList), folder + year.toString() + ".xlsx");
            }
        } catch (IOException e) {
            log.error(STUDENT_EXPORT_TO_CLOUD_ERROR_MESSAGE, e);
            throw new ExportException(STUDENT_EXPORT_TO_CLOUD_ERROR_MESSAGE);
        }
    }

}
