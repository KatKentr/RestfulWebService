package com.springboot.rest.webservices.socialmediaapp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.springboot.rest.webservices.socialmediaapp.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.rest.webservices.socialmediaapp.model.User;
import com.springboot.rest.webservices.socialmediaapp.repository.UserRepository;



//@Service
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
		System.out.println("Inside loadUserByUsername method");
		  System.out.println(user.getUsername());
		System.out.println(user.getEmail());
		System.out.println(user.getRoles().isEmpty());
		user.getRoles().stream().forEach(r->System.out.println(r.getId()+r.getName()));
	       return new org.springframework.security.core.userdetails.User(user.getEmail(),
	       user.getPassword(), getAuthorities(user.getRoles()));                     		 
		
				
	}

//	private static List<GrantedAuthority> getAuthorities(String roles) {
//		List<GrantedAuthority> authorities = new ArrayList<>();
//		String[] UserRoles = roles.split(",");
//		for (String role: UserRoles) {
//			authorities.add(new SimpleGrantedAuthority(role));
//		}
//
//		return authorities;
//	}

	private Set<GrantedAuthority> getAuthorities(Set<Role> roles){

		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

	}

}
