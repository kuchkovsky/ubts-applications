package ua.org.ubts.applications.configuration;

import io.jsonwebtoken.impl.crypto.MacProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.context.annotation.Bean;
import ua.org.ubts.applications.filter.JwtAuthenticationFilter;
import ua.org.ubts.applications.filter.JwtAuthorizationFilter;
import ua.org.ubts.applications.filter.StudentFilesDownloadFilter;
import ua.org.ubts.applications.service.StudentFilesTokenService;

import javax.crypto.SecretKey;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private StudentFilesTokenService studentFilesTokenService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
				.antMatchers(HttpMethod.GET,
						"/students/{id}").access("hasRole('USER') or hasIpAddress('::1')")
				.antMatchers(HttpMethod.POST, "/students", "/files/students").permitAll()
				.antMatchers(HttpMethod.POST, "/students/*/pastor-feedback", "/students/*/friend-feedback").permitAll()
				.antMatchers(HttpMethod.DELETE, "/files/students").permitAll()
				.antMatchers(HttpMethod.HEAD, "/registration/students", "/files/students").permitAll()
				.antMatchers(HttpMethod.GET, "/students/*/full-name", "/files/students/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.addFilter(new JwtAuthenticationFilter(authenticationManager(), secretKey()))
				.addFilter(new JwtAuthorizationFilter(authenticationManager(), secretKey(), userDetailsService))
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Bean
	public FilterRegistrationBean someFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new StudentFilesDownloadFilter(studentFilesTokenService));
		registration.addUrlPatterns("/files/students/*");
		return registration;
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecretKey secretKey() {
		return MacProvider.generateKey();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true);
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD"));
		configuration.addAllowedOrigin("*");
		configuration.addAllowedHeader("*");
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
