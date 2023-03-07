package com.springboot.rest.webservices.socialmediaapp.payload;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class SignUpDto {
	
	@Past(message="Birth date should be in the past")
	private LocalDate birthDate;
	
	@Size(min=2, message="Name should have at least 2 characters")
    private String username;
	
	@Email(message = "Email should be valid")
	@NotEmpty(message = "Email cannot be empty")
    private String email;
	
	@Size(min=4, message="Password should be at least 4 characters")
	@NotEmpty(message = "Password cannot be empty")
    private String password;
	
	@NotEmpty                 //an initial naive approach for roles
    private String roles;
    
	public SignUpDto(LocalDate birthDate, String username, String email, String password, String roles) {
		super();
		this.birthDate = birthDate;
		this.username = username;
		this.email = email;
		this.password = password;
		this.roles=roles;
	}
	public LocalDate getDate() {
		return birthDate;
	}
	public void setDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	public String getName() {
		return username;
	}
	public void setName(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
    
    
    

}
