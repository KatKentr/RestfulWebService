package com.springboot.rest.webservices.restfulwebservices.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;

@Entity
public class Comment {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Size(min= 1)
	private String description;
	
	@ManyToOne(fetch= FetchType.LAZY)        //Lazy: when we fetch the comment, we don't really want to fetch the user details that are associated with the post
	@JsonIgnore
	private User user;
	
	@ManyToOne(fetch= FetchType.LAZY)        //Lazy: when we fetch the comment, we don't really want to fetch the user details that are associated with the post
	//@JsonIgnore
	private Post post;
	
	
     private Comment() {  //default constructor. Public or private?
    	 		
	  }


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Post getPost() {
		return post;
	}


	public void setPost(Post post) {
		this.post = post;
	}


	@Override
	public String toString() {
		return "Comment [id=" + id + ", description=" + description + ", user=" + user + ", post=" + post + ", getId()="
				+ getId() + ", getDescription()=" + getDescription() + ", getUser()=" + getUser() + ", getPost()="
				+ getPost() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
     
     
     
	

}
