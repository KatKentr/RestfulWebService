package com.springboot.rest.webservices.socialmediaapp.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class LoginDto {
	

	@NotEmpty(message = "Username or Email cannot be empty")	
	private String usernameOrEmail;
	
	@Size(min=4, message="Password should be at least 4 characters")
	@NotEmpty(message = "Password cannot be empty")	
    private String password;
    
    
	public LoginDto(String usernameOrEmail, String password) {
		super();
		this.usernameOrEmail = usernameOrEmail;
		this.password = password;
	}


	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}


	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
    
	
	
    

}
