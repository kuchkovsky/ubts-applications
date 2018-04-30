package ua.org.ubts.applications.service;

import org.springframework.security.core.Authentication;
import ua.org.ubts.applications.dto.TokenDto;

public interface StudentFilesTokenService {

    TokenDto getDownloadToken(Long id, Authentication authentication);

    boolean verifyToken(String token);

}
