package ua.org.ubts.applicationssystem.service;

import ua.org.ubts.applicationssystem.entity.User;
import ua.org.ubts.applicationssystem.service.template.BasicService;
import ua.org.ubts.applicationssystem.service.template.ExtendedElementOperationsService;

public interface UserService extends BasicService<User, Integer>, ExtendedElementOperationsService<User> {

    User findByLogin(String login);

}
