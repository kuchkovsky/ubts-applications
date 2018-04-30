package ua.org.ubts.applications.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.org.ubts.applications.converter.ProgramConverter;
import ua.org.ubts.applications.dto.ProgramDto;
import ua.org.ubts.applications.service.ProgramService;

import java.util.List;

@RestController
@RequestMapping("/api/programs")
public class ProgramApiController {

    @Autowired
    private ProgramService programService;

    @Autowired
    private ProgramConverter programConverter;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<ProgramDto> getPrograms() {
        return programConverter.convertToDto(programService.getPrograms());
    }

}
