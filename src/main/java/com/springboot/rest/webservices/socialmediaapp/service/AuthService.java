package com.springboot.rest.webservices.socialmediaapp.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.springboot.rest.webservices.socialmediaapp.model.User;
import com.springboot.rest.webservices.socialmediaapp.payload.LoginDto;
import com.springboot.rest.webservices.socialmediaapp.payload.SignUpDto;
import com.springboot.rest.webservices.socialmediaapp.repository.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthService {
	
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private AuthenticationManager authenticationManager;
	
		
	public AuthService(UserRepository userRepository,PasswordEncoder passwordEncoder,AuthenticationManager authenticationManager) {
		super();
		this.userRepository = userRepository;
		this.authenticationManager=authenticationManager;
		this.passwordEncoder=passwordEncoder;
	}
	
	
	public ResponseEntity<String> login(LoginDto loginDto){
		
		 Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
	                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
				
	}


	public ResponseEntity<String> register(SignUpDto signUpDto) {
		
		
		// add check for username exists in a DB
        if(userRepository.existsByUsername(signUpDto.getName())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        User user = new User();
        user.setDate(signUpDto.getDate());
        user.setName(signUpDto.getName());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setRoles(signUpDto.getRoles());
        
        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    			
	}
	
	
	//retrieve current user
	public User getCurrentUser() {
        var principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(principal.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"E-mail not found"));
    }
	
	

	

}
