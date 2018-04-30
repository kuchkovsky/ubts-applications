package ua.org.ubts.applications.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.org.ubts.applications.service.StudentRegistrationService;

@RestController
@RequestMapping("/api/registration/students")
public class StudentRegistrationApiController {

    @Autowired
    private StudentRegistrationService studentRegistrationService;

    @RequestMapping(method = RequestMethod.HEAD)
    public void checkRegistrationStatus() {
        studentRegistrationService.checkStudentRegistrationStatus();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public void openStudentRegistration() {
        studentRegistrationService.openStudentRegistration();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public void closeStudentRegistration() {
        studentRegistrationService.closeStudentRegistration();
    }

}
