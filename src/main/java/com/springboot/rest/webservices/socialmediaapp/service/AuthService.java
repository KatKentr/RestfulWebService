package com.springboot.rest.webservices.socialmediaapp.service;

import com.springboot.rest.webservices.socialmediaapp.model.Role;
import com.springboot.rest.webservices.socialmediaapp.repository.RoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.springboot.rest.webservices.socialmediaapp.exception.DuplicateEmailException;
import com.springboot.rest.webservices.socialmediaapp.exception.DuplicateUsernameException;
import com.springboot.rest.webservices.socialmediaapp.model.User;
import com.springboot.rest.webservices.socialmediaapp.payload.LoginDto;
import com.springboot.rest.webservices.socialmediaapp.payload.SignUpDto;
import com.springboot.rest.webservices.socialmediaapp.repository.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;


@Service
public class AuthService {
	
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private AuthenticationManager authenticationManager;

	private RoleRepository roleRepository;
		
	public AuthService(UserRepository userRepository,PasswordEncoder passwordEncoder,AuthenticationManager authenticationManager,RoleRepository roleRepository) {
		super();
		this.userRepository = userRepository;
		this.authenticationManager=authenticationManager;
		this.passwordEncoder=passwordEncoder;
		this.roleRepository=roleRepository;
	}
	
	
	public ResponseEntity<String> login(LoginDto loginDto){
		
		 Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
	                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
				
	}


	public void register(SignUpDto signUpDto) {
		
		
		// add check for username exists in a DB
        if(userRepository.existsByUsername(signUpDto.getName())){
            throw new DuplicateUsernameException();
        }

        // add check for email exists in DB
        if(userRepository.existsByEmail(signUpDto.getEmail())){
        	throw new DuplicateEmailException();
        }

        // create user object
        User user = new User();
        user.setDate(signUpDto.getDate());
        user.setName(signUpDto.getName());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        //user.setRoles(signUpDto.getRoles());
		Set<Role> roles=signUpDto.getRoles();

		Set<Role> user_roles=roles.stream().map(r -> roleRepository.findByRoleType(r.getRole_type()).get()).collect(toSet());
        user.setRoles(user_roles);
        userRepository.save(user);
   			
	}
	
	
	//retrieve current user
	public User getCurrentUser() {
        var principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(principal.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"E-mail not found"));
    }
	
	

	

}
