package com.springboot.rest.webservices.socialmediaapp.service;

import com.springboot.rest.webservices.socialmediaapp.jwt.JwtTokenUtil;
import com.springboot.rest.webservices.socialmediaapp.model.Role;
import com.springboot.rest.webservices.socialmediaapp.payload.AuthRequest;
import com.springboot.rest.webservices.socialmediaapp.payload.AuthResponse;
import com.springboot.rest.webservices.socialmediaapp.repository.RoleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
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

	@Autowired
	JwtTokenUtil jwtUtil;
		
	public AuthService(UserRepository userRepository,PasswordEncoder passwordEncoder,AuthenticationManager authenticationManager,RoleRepository roleRepository) {
		super();
		this.userRepository = userRepository;
		this.authenticationManager=authenticationManager;
		this.passwordEncoder=passwordEncoder;
		this.roleRepository=roleRepository;
	}
	
	
//	public ResponseEntity<String> login(LoginDto loginDto){
//
//		 Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//	                loginDto.getUsernameOrEmail(), loginDto.getPassword()));
//         System.out.println("inside login method");
//	        SecurityContextHolder.getContext().setAuthentication(authentication);
//	        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
//
//	}

	/*
 Method login taken from a tutorial article written from Nam Ha Minh at CodeJava:
 https://www.codejava.net/frameworks/spring-boot/spring-security-jwt-role-based-authorization
 Source: https://github.com/codejava-official/spring-jwt-authorization retrieved in May 2023
  */
	public ResponseEntity<?> login(@Valid AuthRequest request) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							request.getEmail(), request.getPassword())
			);

			User user = (User) authentication.getPrincipal();
			String accessToken = jwtUtil.generateAccessToken(user);
			AuthResponse response = new AuthResponse(user.getEmail(), accessToken);
			System.out.println("inside login method "+user.getRoles());
			return ResponseEntity.ok().body(response);

		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
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
        user.setUsername(signUpDto.getName());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        //user.setRoles(signUpDto.getRoles());
		Set<Role> roles=signUpDto.getRoles();

		Set<Role> user_roles=roles.stream().map(r -> roleRepository.findByName(r.getName()).get()).collect(toSet());
        user.setRoles(user_roles);
        user_roles.forEach(r-> r.addUser(user));                            //update also the set of users for each role
        userRepository.save(user);
   			
	}
	
	
	//retrieve current user
//	public User getCurrentUser() {
//        var principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return userRepository.findByEmail(principal.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"E-mail not found"));
//    }
	

	//retrieve the current user from the security context holder
     public User getCurrentUser(){

		System.out.println("inside getCurrentUser");
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 User userDetails = (User) authentication.getPrincipal(); //we retrieve email and roles

// getEmail() - Returns the e-mail used to authenticate the user.
		 //System.out.println("User name: " + userDetails.getUsername());   //returns null. username is not retrieved from the authenticated principal
		 System.out.println("User name: " + userDetails.getEmail());

// getAuthorities() - Returns the authorities granted to the user.
		 System.out.println("User has authorities: " + userDetails.getAuthorities());
		 return userDetails;
	 }
	

}
