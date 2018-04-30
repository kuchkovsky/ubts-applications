package ua.org.ubts.applications.filter;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ua.org.ubts.applications.constants.JwtConstants.HEADER_STRING;
import static ua.org.ubts.applications.constants.JwtConstants.TOKEN_PREFIX;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private static final String USERNAME_NOT_FOUND_MESSAGE = "Could not find user with name=";

	private SecretKey secretKey;

	private final UserDetailsService userDetailsService;

	public JwtAuthorizationFilter(AuthenticationManager authManager, SecretKey secretKey,
								  UserDetailsService userDetailsService) {
		super(authManager);
		this.secretKey = secretKey;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String header = request.getHeader(HEADER_STRING);
		if (header == null || !header.startsWith(TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
		try {
			UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (JwtException e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		if (token != null) {
			String userName = Jwts.parser()
					.setSigningKey(secretKey)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
					.getBody()
					.getSubject();
			if (userName != null) {
				try {
					UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
					return new UsernamePasswordAuthenticationToken(userName, null, userDetails.getAuthorities());
				} catch (UsernameNotFoundException e) {
					logger.error(USERNAME_NOT_FOUND_MESSAGE + userName, e);
				}
			}
		}
		return null;
	}

}
