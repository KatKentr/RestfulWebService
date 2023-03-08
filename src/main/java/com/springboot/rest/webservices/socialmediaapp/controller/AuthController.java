package com.springboot.rest.webservices.socialmediaapp.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springboot.rest.webservices.socialmediaapp.constants.ApiRoutes;
import com.springboot.rest.webservices.socialmediaapp.model.User;
import com.springboot.rest.webservices.socialmediaapp.payload.LoginDto;
import com.springboot.rest.webservices.socialmediaapp.payload.SignUpDto;
import com.springboot.rest.webservices.socialmediaapp.service.AuthService;

import jakarta.validation.Valid;

@RestController
public class AuthController {
	

    private AuthService authService;
    
    public AuthController(AuthService authService) {           //constructor-based injection
    	
    	this.authService=authService;
    }

  
    @PostMapping(ApiRoutes.Auth.LOGIN)
    public ResponseEntity<String> authenticateUser(@Valid @RequestBody LoginDto loginDto){
       return authService.login(loginDto);
    }
    
    @PostMapping(ApiRoutes.Auth.REGISTER)
    public ResponseEntity<User> registerUser(@Valid @RequestBody SignUpDto signUpDto){ //By using ResponseEntity we get status 201
    	
    	User savedUser= authService.register(signUpDto);
    	// return also the URI(location) of the user created e.g: /users/4 . The user
		// can then type the Uri and get the details of the newly created user back
		// Whenever we want to write a URL of a created resource , we have to use the
		// Location HTTP header    	
    	URI location = ServletUriComponentsBuilder.fromUriString(ApiRoutes.User.GET_ALL).path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();


}
}
