package com.springboot.rest.webservices.socialmediaapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rest.webservices.socialmediaapp.constants.ApiRoutes;
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
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpDto signUpDto){
    	
    	return authService.register(signUpDto);

}
}
