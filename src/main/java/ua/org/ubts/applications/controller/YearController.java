package ua.org.ubts.applications.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.org.ubts.applications.converter.YearConverter;
import ua.org.ubts.applications.dto.YearDto;
import ua.org.ubts.applications.service.YearService;

import java.util.List;

@RestController
@RequestMapping("/years")
public class YearController {

    @Autowired
    private YearService yearService;

    @Autowired
    private YearConverter yearConverter;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<YearDto> getYears() {
        return yearConverter.convertToDto(yearService.getYears());
    }

}
