package com.springboot.rest.webservices.socialmediaapp.exception;

import java.net.http.HttpHeaders;
import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
	
	
	@ExceptionHandler(Exception.class)   //we want to handle all exceptions
	public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), 
				ex.getMessage(), request.getDescription(false));    //we are making use of our own custom exception structure and we are returning it back as the response
		
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@ExceptionHandler({UserNotFoundException.class,PostNotFoundException.class,CommentNotFoundException.class})   //we want to handle the eception User not found: returns 404
	public final ResponseEntity<ErrorDetails> handleResourceNotFoundException(Exception ex, WebRequest request) throws Exception {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), 
				ex.getMessage(), request.getDescription(false));    //we are making use of our own custom exception structure and we are returning it back as the response
		
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler({DuplicateEmailException.class,DuplicateUsernameException.class})   // returns 409
	public final ResponseEntity<ErrorDetails> handleDuplicateException(Exception ex, WebRequest request) throws Exception {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), 
				ex.getMessage(), request.getDescription(false));    //we are making use of our own custom exception structure and we are returning it back as the response
		
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.CONFLICT);
		
	}
	
	@ExceptionHandler({UserNotAllowedException.class,org.springframework.security.access.AccessDeniedException.class})   //returns 403
	public final ResponseEntity<ErrorDetails> handlePermissionException(Exception ex, WebRequest request) throws Exception {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), 
				ex.getMessage(), request.getDescription(false));    //we are making use of our own custom exception structure and we are returning it back as the response
		
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.FORBIDDEN);
		
	}


	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {    //attempt to display an appropriate error message for invalid input to post request for a new user. Did not work though
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), 
				"Total Errors:" + ex.getErrorCount() + " First Error:" + ex.getFieldError().getDefaultMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

}
