package ua.org.ubts.applications.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.org.ubts.applications.dto.TokenDto;
import ua.org.ubts.applications.dto.UserCredentialsDto;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import static ua.org.ubts.applications.constants.JwtConstants.EXPIRATION_TIME;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private static final String CREDENTIALS_READ_ERROR_MESSAGE = "Failed to read credentials";

	private static final String TOKEN_WRITE_ERROR_MESSAGE = "Could not write token to response object";

	private AuthenticationManager authenticationManager;

	private SecretKey secretKey;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, SecretKey secretKey) {
		this.authenticationManager = authenticationManager;
		this.secretKey = secretKey;
		this.setFilterProcessesUrl("/auth/login");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			UserCredentialsDto credentials = new ObjectMapper()
					.readValue(request.getInputStream(), UserCredentialsDto.class);
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							credentials.getLogin(),
							credentials.getPassword(),
							new ArrayList<>())
			);
		} catch (IOException e) {
			throw new BadCredentialsException(CREDENTIALS_READ_ERROR_MESSAGE);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
											Authentication auth) {
		String username = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();
		Claims claims = Jwts.claims().setSubject(username);
		claims.put("roles", auth.getAuthorities().stream().map(Object::toString).collect(Collectors.toList()));
		String token = Jwts.builder()
				.setClaims(claims)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, secretKey)
				.compact();
		res.setContentType("application/json");
		try {
			new ObjectMapper().writeValue(res.getWriter(), new TokenDto(token));
		} catch (IOException e) {
			log.error(TOKEN_WRITE_ERROR_MESSAGE, e);
		}
	}

}
