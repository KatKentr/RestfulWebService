package com.springboot.rest.webservices.socialmediaapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.CONFLICT)
public class DuplicateUsernameException extends RuntimeException {
	
	public DuplicateUsernameException() {
		super("Username is already taken!");
	}

}
