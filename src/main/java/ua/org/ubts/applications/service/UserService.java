package ua.org.ubts.applications.service;

import org.springframework.security.core.Authentication;
import ua.org.ubts.applications.entity.UserEntity;

import java.security.Principal;

public interface UserService {

    UserEntity getUser(Integer id);

    UserEntity getUser(Principal principal);

    UserEntity getUser(Authentication authentication);

    boolean isUserExists(Integer id);

}
