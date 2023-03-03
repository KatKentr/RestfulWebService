package com.springboot.rest.webservices.restfulwebservices.payload;

import java.time.LocalDate;

public class SignUpDto {
	
	private LocalDate date;
    private String name;
    private String email;
    private String password;
    private String role;
    
	public SignUpDto(LocalDate date, String name, String email, String password, String role) {
		super();
		this.date = date;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role=role;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
		return role;
	}
	public void setRoles(String role) {
		this.role = role;
	}
    
    
    

}
