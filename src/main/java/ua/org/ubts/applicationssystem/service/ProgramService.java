package ua.org.ubts.applicationssystem.service;

import ua.org.ubts.applicationssystem.entity.Program;
import ua.org.ubts.applicationssystem.service.template.BasicService;
import ua.org.ubts.applicationssystem.service.template.ExtendedElementOperationsService;

public interface ProgramService extends BasicService<Program, Long>, ExtendedElementOperationsService<Program> {

    Program findByNameAndInfo(String name, String info);

}
