package ua.org.ubts.applications.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ua.org.ubts.applications.entity.UserEntity;
import ua.org.ubts.applications.exception.UserNotFoundException;
import ua.org.ubts.applications.repository.UserRepository;
import ua.org.ubts.applications.service.UserService;

import javax.transaction.Transactional;
import java.security.Principal;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private static final String USER_ID_NOT_FOUND_MESSAGE = "Could not find user with id=";
    private static final String USER_LOGIN_NOT_FOUND_MESSAGE = "Could not find user with login=";

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity getUser(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(USER_ID_NOT_FOUND_MESSAGE + id));
    }

    @Override
    public UserEntity getUser(Principal principal) {
        return userRepository.findByLogin(principal.getName()).orElseThrow(() ->
                new UserNotFoundException(USER_LOGIN_NOT_FOUND_MESSAGE + principal.getName()));
    }

    @Override
    public UserEntity getUser(Authentication authentication) {
        String email =  ((String) authentication.getPrincipal());
        return userRepository.findByLogin(email).orElseThrow(() ->
                new UserNotFoundException(USER_LOGIN_NOT_FOUND_MESSAGE + email));
    }

    @Override
    public boolean isUserExists(Integer id) {
        return userRepository.findById(id).isPresent();
    }

}
