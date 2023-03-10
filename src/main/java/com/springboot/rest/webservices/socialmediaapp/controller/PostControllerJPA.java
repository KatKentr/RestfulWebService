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
import com.springboot.rest.webservices.socialmediaapp.model.Post;
import com.springboot.rest.webservices.socialmediaapp.model.User;
import com.springboot.rest.webservices.socialmediaapp.service.PostService;

import jakarta.validation.Valid;

@RestController
public class PostControllerJPA {

	private PostService postService;

	public PostControllerJPA(PostService postService) {

		this.postService = postService;

	}

	// retrieve all posts of a user
	@GetMapping(ApiRoutes.Post.GET_BY_USERID)
	public List<Post> retrievePostsForAUser(@PathVariable int userId) {

		return postService.getPostsFromUser(userId);

	}

	// create a new Post
	@PostMapping(ApiRoutes.Post.CREATE)
	public ResponseEntity<Post> createPostForUser(@Valid @RequestBody Post post) {

		Post newPost = postService.saveNewPost(post);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newPost.getId())
				.toUri(); // build the URI for the new post
		return ResponseEntity.created(location).build();  //Location HTTP header

	}
	
	//retrieve all posts
	@GetMapping(ApiRoutes.Post.GET_ALL)
	public List<Post> retrieveAllPosts(){
		
		return postService.getAllPosts();
	}
	
	// retrieve the details of a specific post
	@GetMapping(ApiRoutes.Post.GET_BY_ID)
	public EntityModel<Post> getPostDetails(@PathVariable int id) {
		
		Optional<Post> post = postService.getPostDetails(id);
		User userOfPost=post.get().getUser(); //get the user of this post
		int userId=userOfPost.getId();
		
		EntityModel<Post> entityModel = EntityModel.of(post.get());
		
		// points to the method of the controller retrievePostsForAuser
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrievePostsForAUser(userId)); // provide a link to  the consumer of the API to inform on how to retrieve all posts of this user	
																									
		entityModel.add(link.withRel("all-posts-from-this-user"));

		return entityModel;
		
			
	}
	
	// Users that are not admins, will be able to delete only their own posts

	@DeleteMapping(ApiRoutes.Post.DELETE_BY_ID)
	public void deletePost(@PathVariable int id) {

		postService.deletePostOfUserById(id);

	}
	

	
	//TO THINK: Could we provide two routes to the same resource?
	//e.g: app/v1/users/{userId}/posts/{postId}  + app/v1/posts/{postId}
	// retrieve the details of a specific post

//	@GetMapping(path = "/jpa/users/{userId}/posts/{PostId}")
//	public EntityModel<Post> retrievePost(@PathVariable int userId, @PathVariable int PostId) {
//
//		Optional<Post> post = postService.getPostDetails(userId, PostId);
//
//		EntityModel<Post> entityModel = EntityModel.of(post.get());
//
//		// points to the method of the controller retrievePostsForAuser
//		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrievePostsForAUser(userId)); // provide a link to  the consumer of the API to inform on how to retrieve all posts of a user	
//																							
//		entityModel.add(link.withRel("all-posts-from-user"));
//
//		return entityModel;
//
//	}



}
