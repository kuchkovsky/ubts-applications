package ua.org.ubts.applications.service.impl;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ua.org.ubts.applications.dto.TokenDto;
import ua.org.ubts.applications.entity.UserEntity;
import ua.org.ubts.applications.service.StudentFilesTokenService;
import ua.org.ubts.applications.service.StudentService;
import ua.org.ubts.applications.service.UserService;

import javax.crypto.SecretKey;
import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class StudentFilesTokenServiceImpl implements StudentFilesTokenService {

    private static final long TOKEN_EXPIRATION_TIME = 2000; // 2s

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private SecretKey secretKey;

    @Override
    public TokenDto getDownloadToken(Long id, Authentication authentication) {
        UserEntity userEntity = userService.getUser(authentication);
        Claims claims = Jwts.claims().setSubject(String.valueOf(userEntity.getId()));
        claims.put("studentId", id);
        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
        return new TokenDto(token);
    }

    @Override
    public boolean verifyToken(String token) {
        Integer userId;
        Long studentId;
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            userId = Integer.parseInt(claims.getBody().getSubject());
            studentId = ((Integer) claims.getBody().get("studentId")).longValue();
        } catch (JwtException | NumberFormatException e) {
            return false;
        }
        return userService.isUserExists(userId) && studentService.isStudentExists(studentId);
    }

}
