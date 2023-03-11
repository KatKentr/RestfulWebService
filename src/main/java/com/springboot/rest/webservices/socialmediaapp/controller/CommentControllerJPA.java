package com.springboot.rest.webservices.socialmediaapp.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	@GetMapping(ApiRoutes.Comment.GET_BY_POST)
	public List<Comment> retrieveCommentsOfPost(@PathVariable int postId) {
		return commentService.getCommentsFromPost(postId);

	}
	
	//create a new comment.
	//According to the current schema a comment is related to a user and and a post -> Both are foreign keys
	//The authenticated user can create a comment to a post
	@PostMapping(ApiRoutes.Comment.CREATE)
	public ResponseEntity<Comment> createCommentForUser(@Valid @RequestBody Comment commment,@PathVariable int postId) {

	Comment newComment=commentService.saveNewComment(commment, postId);
	URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newComment.getId())
				.toUri(); // build the URI for the new comment
	return ResponseEntity.created(location).build();
   }
	
	//retrieve a comment
	@GetMapping(ApiRoutes.Comment.GET_BY_ID)
	public EntityModel<Comment> getCommentDetails(@PathVariable int commentId){
		
		Optional<Comment> comment=commentService.getCommentDetails(commentId);
		
		Post post=comment.get().getPost();  //get the post that the commnet relates to 
		
        EntityModel<Comment> entityModel = EntityModel.of(comment.get());
		
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveCommentsOfPost(post.getId())); // provide a link to  the consumer of the API to inform on how to retrieve all comments of a post	
																									
		entityModel.add(link.withRel("all-comments-of-this-post"));

		return entityModel;
		
	}
	
	
	@DeleteMapping(ApiRoutes.Comment.GET_BY_ID)   //authenticated users can delete their own comments
	public void deleteComment(@PathVariable int commentId) {
		
		commentService.deleteCommentOfUserById(commentId);
		
	}
	

}
