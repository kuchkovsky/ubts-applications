package ua.org.ubts.applications.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.org.ubts.applications.service.StudentExportService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks/students")
public class StudentTasksController {

    @Autowired
    private StudentExportService studentExportService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/export-excel")
    public void exportStudentsToExcel(@RequestBody Optional<List<Integer>> years) {
        studentExportService.exportStudentsToExcel(years);
    }

}
