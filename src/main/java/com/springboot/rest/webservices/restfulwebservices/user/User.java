package com.springboot.rest.webservices.restfulwebservices.user;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity(name="user_details")     //tell JPA to manage this. User is a keyword in H2. We encountered error, so we change the table name
public class User {              //the table will be automatically created when we launch the application, when we use the H2 database
	
    @Id
    @GeneratedValue
	private Integer id;
	
	@Size(min=2, message="Name should have at least 2 characters")
	private String name;
	
	@Past(message="Birth date should be in the past")
	private LocalDate date;
	
	//A user will have a list of posts
	@OneToMany(mappedBy="user")   //the field in the Post class that owns this relationship
	@JsonIgnore                //we don't want post to be part of the json reponses for the user bean
	private List<Post> posts;
	
	
	public User() {   //we need a default constructor hen we make use of jpa
		
	}
	
	
	public User(Integer id, String name, LocalDate date) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
	}


	public int getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", date=" + date + "]";
	}
	
	
	

}
