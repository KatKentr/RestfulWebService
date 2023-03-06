package com.springboot.rest.webservices.restfulwebservices.controller;

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

import com.springboot.rest.webservices.restfulwebservices.model.Post;
import com.springboot.rest.webservices.restfulwebservices.service.PostService;

import jakarta.validation.Valid;

@RestController
public class PostControllerJPA {

	private PostService postService;

	public PostControllerJPA(PostService postService) {

		this.postService = postService;

	}

	// retrieve all posts of a user
	@GetMapping(path = "jpa/users/{id}/posts")
	public List<Post> retrievePostsForAUser(@PathVariable int id) {

		return postService.getPostsFromUser(id);

	}

	// create a new Post for a specific user
	@PostMapping(path = "jpa/users/{id}/posts")
	public ResponseEntity<Post> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post) {

		Post newPost = postService.saveNewPost(id, post);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newPost.getId())
				.toUri(); // build the URI for the ne post
		return ResponseEntity.created(location).build();

	}

	// retrieve the details of a specific post

	@GetMapping(path = "/jpa/users/{userId}/posts/{PostId}")
	public EntityModel<Post> retrievePost(@PathVariable int userId, @PathVariable int PostId) {

		Optional<Post> post = postService.getPostDetails(userId, PostId);

		EntityModel<Post> entityModel = EntityModel.of(post.get());

		// points to the method of the controller retrievePostsForAuser
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrievePostsForAUser(userId)); // provide a link to  the consumer of the API to inform on how to retrieve all posts of a user	
																							
		entityModel.add(link.withRel("all-posts-from-user"));

		return entityModel;

	}

	// Delete a post

	@DeleteMapping(path = "jpa/users/{user_id}/posts/{id}")
	public void deletePost(@PathVariable int user_id, @PathVariable int id) {

		postService.deletePostOfUserById(user_id, id);

	}

}