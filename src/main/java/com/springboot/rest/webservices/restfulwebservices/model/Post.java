package com.springboot.rest.webservices.restfulwebservices.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;

@Entity
public class Post {
	
	@Id
    @GeneratedValue
	private Integer id;
	
	@Size(min= 10)
	private String description;
	
	@ManyToOne(fetch= FetchType.LAZY)        //Lazy: when we fetch the post, we don't really want to fetch the user details that are associated with the post
	@JsonIgnore
	private User user;
	
	
	//A post may have multiple comments
	@OneToMany(mappedBy="post",cascade = CascadeType.ALL,orphanRemoval = true)   //the field in the Post class that owns this relationship. CascadeType.All and orphanRemoval true: Child entities (post) of a user (parent entity) will be deleted, when the user is deleted
	@JsonIgnore                //we don't want comment to be part of the json reponses for the post bean
	private List<Comment> comments;
		
	
	
	private Post() {
		
	}

	public Post(Integer id, String description, User user) {
		super();
		this.id = id;
		this.description = description;
		this.user=user;
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
	
	

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", description=" + description + "]";
	}
	
	
	
	

}
