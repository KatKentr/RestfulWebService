package com.springboot.rest.webservices.restfulwebservices.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.rest.webservices.restfulwebservices.model.User;
import com.springboot.rest.webservices.restfulwebservices.repository.UserRepository;



@Service
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		
		  User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
	                 .orElseThrow(() ->
	                         new UsernameNotFoundException("User not found with username or email: "+ usernameOrEmail));
		
	       return new org.springframework.security.core.userdetails.User(user.getEmail(),
	       user.getPassword(), getAuthorities(user.getRoles()));                     		 
		
				
	}

	private static List<GrantedAuthority> getAuthorities(String roles) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		String[] UserRoles = roles.split(",");
		for (String role: UserRoles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		
		return authorities;
	}

}
