package ua.org.ubts.applications.service;

public interface StudentExportService {

    void exportStudentToCloud(Long id);

    void exportAllStudentsToCloud();

    void exportAllStudentsToExcel();

}
