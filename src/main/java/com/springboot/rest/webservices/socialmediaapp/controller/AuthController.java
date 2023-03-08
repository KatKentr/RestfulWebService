package com.springboot.rest.webservices.socialmediaapp.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springboot.rest.webservices.socialmediaapp.constants.ApiRoutes;
import com.springboot.rest.webservices.socialmediaapp.model.User;
import com.springboot.rest.webservices.socialmediaapp.payload.LoginDto;
import com.springboot.rest.webservices.socialmediaapp.payload.SignUpDto;
import com.springboot.rest.webservices.socialmediaapp.service.AuthService;
import org.springframework.http.HttpStatus;

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
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@Valid @RequestBody SignUpDto signUpDto){ //By using ResponseEntity we get status 201
    	
    	authService.register(signUpDto);
 
}
    
}
