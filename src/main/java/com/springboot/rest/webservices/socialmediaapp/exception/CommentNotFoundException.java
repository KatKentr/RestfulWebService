package com.springboot.rest.webservices.socialmediaapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND)
public class CommentNotFoundException extends RuntimeException {
	
	public CommentNotFoundException(String message) {
		super("Comment with " +message+ " not found");
	}

}
