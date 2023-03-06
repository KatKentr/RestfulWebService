package com.springboot.rest.webservices.restfulwebservices.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor


//@Builder
@Entity(name="user_details")     //tell JPA to manage this. User is a keyword in H2. We encountered error, so we change the table name
public class User {              //the table will be automatically created when we launch the application, when we use the H2 database
	
    @Id
    @GeneratedValue
	private Integer id;
	
	@Size(min=2, message="Name should have at least 2 characters")
	@Column(name="name")
	//private String name;
	private String username;
	
	@Past(message="Birth date should be in the past")
	private LocalDate date;
	
	
	//add validatios for these
	private String email;
	private String password;
	
	
	//A user will have a list of posts
	@OneToMany(mappedBy="user",cascade = CascadeType.ALL,orphanRemoval = true)   //the field in the Post class that owns this relationship. CascadeType.All and orphanRemoval true: Child entities (post) of a user (parent entity) will be deleted, when the user is deleted
	@JsonIgnore                //we don't want post to be part of the json reponses for the user bean
	private List<Post> posts;
	
	//A user may have multiple comments
	@OneToMany(mappedBy="user",cascade = CascadeType.ALL,orphanRemoval = true)   //the field in the Post class that owns this relationship. CascadeType.All and orphanRemoval true: Child entities (post) of a user (parent entity) will be deleted, when the user is deleted
	@JsonIgnore                //we don't want post to be part of the json reponses for the user bean
	private List<Comment> comments;
	
	private String  roles;
	
	
	public User() {   //we need a default constructor hen we make use of jpa
		
	}
	
	
	public User(Integer id, String name, LocalDate date, String email,String password, String roles) {
		super();
		this.id = id;
		this.username = name;
		this.date = date;
		this.email=email;
		this.password=password;
		this.roles=roles;
	}


	public int getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return username;
	}


	public void setName(String name) {
		this.username = name;
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}

	
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
	public List<Post> getPosts() {
		return posts;
	}
	
	//comments
	
	public List<Comment> getComments() {
		return comments;
	}


	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	
	//email, password
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


	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + username + ", date=" + date + "]";
	}



	
	
	

}