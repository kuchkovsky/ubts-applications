package ua.org.ubts.applications.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.org.ubts.applications.dto.YearDto;
import ua.org.ubts.applications.service.StudentExportService;

import java.util.List;
import java.util.Optional;

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
    public void exportStudentsToCloud(@RequestBody Optional<List<Integer>> years) {
        studentExportService.exportStudentsToCloud(years);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/cloud/excel")
    public void exportStudentsToExcel(@RequestBody Optional<List<Integer>> years) { System.out.println(years);
        studentExportService.exportStudentsToExcel(years);
    }

}
