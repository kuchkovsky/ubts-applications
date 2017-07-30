package ua.org.ubts.applicationssystem.service;

import ua.org.ubts.applicationssystem.entity.Student;
import ua.org.ubts.applicationssystem.service.template.BasicService;
import ua.org.ubts.applicationssystem.service.template.ExtendedElementOperationsService;

/**
 * Created by Yaroslav on 18.07.2017.
 */
public interface StudentService extends BasicService<Student, Integer>, ExtendedElementOperationsService<Student> {

    Student findByName(String firstName, String middleName, String lastName);

}
