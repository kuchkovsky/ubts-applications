package ua.org.ubts.applications.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.org.ubts.applications.service.StudentExportService;

@RestController
@RequestMapping("/api/export/students")
public class StudentExportApiController {

    @Autowired
    private StudentExportService studentExportService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/cloud")
    public void exportStudentToCloud(@PathVariable("id") Long id) {
        studentExportService.exportStudentToCloud(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/cloud")
    public void exportAllStudentsToCloud() {
        studentExportService.exportAllStudentsToCloud();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/cloud/excel")
    public void exportAllStudentsToExcel() {
        studentExportService.exportAllStudentsToExcel();
    }

}
