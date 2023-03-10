package com.springboot.rest.webservices.socialmediaapp.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springboot.rest.webservices.socialmediaapp.constants.ApiRoutes;
import com.springboot.rest.webservices.socialmediaapp.model.Comment;
import com.springboot.rest.webservices.socialmediaapp.model.Post;
import com.springboot.rest.webservices.socialmediaapp.service.CommentService;

import jakarta.validation.Valid;

@RestController
public class CommentControllerJPA {

	private CommentService commentService;

	public CommentControllerJPA(CommentService commentService) {

		this.commentService = commentService;

	}

	// retrieve all comments of a USER
	@GetMapping(ApiRoutes.Comment.GET_BY_USERID)
	public List<Comment> retrieveCommentsOfUser(@PathVariable int userId) {

		return commentService.getCommentsFromUser(userId);

	}

	// retrieve all comments of a post
	@GetMapping(path = "jpa/posts/{postId}/comments")
	public List<Comment> retrieveCommentsOfPost(@PathVariable int postId) {
		//TO DO: check that post exists!
		return commentService.getCommentsFromPost(postId);

	}
	
	//create a new comment.
	//According to the current schema a comment is related to a user and and a post -> Both are foreign keys
	//We can relate the comment to a specific post by using the postId path variable. But how can we relate the comment to a specific user?
	@PostMapping(path = "jpa/posts/{postId}/comments")
	public ResponseEntity<Post> createPostForUser(@Valid @RequestBody Comment commment,@PathVariable int postId) {

	Comment newComment=commentService.saveNewComment(commment, postId);
	URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newComment.getId())
				.toUri(); // build the URI for the ne post
	return ResponseEntity.created(location).build();
   }
	
	
	
}
