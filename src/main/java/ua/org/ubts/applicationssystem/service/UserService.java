package ua.org.ubts.applicationssystem.service;

import ua.org.ubts.applicationssystem.entity.User;

import java.util.List;

public interface UserService {
    User findById(Long id);
    User findByUsername(String username);
    List<User> findAll();
}
