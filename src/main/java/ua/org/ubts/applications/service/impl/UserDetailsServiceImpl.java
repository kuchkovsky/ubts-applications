package ua.org.ubts.applications.service.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;
import ua.org.ubts.applications.entity.RoleEntity;
import ua.org.ubts.applications.entity.UserEntity;
import ua.org.ubts.applications.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByLogin(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (RoleEntity roleEntity : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(roleEntity.getName()));
		}
		return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
	}

}
