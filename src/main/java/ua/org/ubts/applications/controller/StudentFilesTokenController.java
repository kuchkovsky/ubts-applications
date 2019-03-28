package ua.org.ubts.applications.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.org.ubts.applications.dto.TokenDto;
import ua.org.ubts.applications.service.StudentFilesTokenService;

@RestController
@RequestMapping("/tokens/files/students")
public class StudentFilesTokenController {

    @Autowired
    private StudentFilesTokenService studentFilesTokenService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public TokenDto getDownloadToken(@PathVariable("id") Long id, Authentication authentication) {
        return studentFilesTokenService.getDownloadToken(id, authentication);
    }

}
