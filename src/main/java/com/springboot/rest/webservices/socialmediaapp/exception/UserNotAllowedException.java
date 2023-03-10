package com.springboot.rest.webservices.socialmediaapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.FORBIDDEN)
public class UserNotAllowedException extends RuntimeException {
	
	public UserNotAllowedException() {
		super("Permission denied");
	}

}
