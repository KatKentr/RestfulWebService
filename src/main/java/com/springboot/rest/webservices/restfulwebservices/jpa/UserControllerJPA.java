package com.springboot.rest.webservices.restfulwebservices.jpa;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springboot.rest.webservices.restfulwebservices.user.Post;
import com.springboot.rest.webservices.restfulwebservices.user.User;
import com.springboot.rest.webservices.restfulwebservices.user.UserNotFoundException;

import jakarta.validation.Valid;

@RestController
public class UserControllerJPA {
	
	private UserRepository userRepository;
	private PostRepository postRepository;
	
	public UserControllerJPA(UserRepository userRepository, PostRepository postRepository) { //constructor-based Injection
		
		this.userRepository=userRepository;
		this.postRepository=postRepository;
	}
	
	@GetMapping(path= "/jpa/users")
	public List<User> retrieveAllUsers() {
		
		return userRepository.findAll();
	}
	
	//we wrap the User class and create an EntityModel in order to use Spring HATEOAS: Generate HAL responses with hyperlinks to resources
		@GetMapping(path="/jpa/users/{id}")
		public EntityModel<User> retrieveUser(@PathVariable int id){
			
			Optional<User> user=userRepository.findById(id);
			
			if (user.isEmpty())
				throw new UserNotFoundException("id:"+id);  //we want to return status 404 and a relevant message
			
			EntityModel<User> entityModel = EntityModel.of(user.get());
			
			//points to the method of the controller retrieveAllUsers
			WebMvcLinkBuilder link =  linkTo(methodOn(this.getClass()).retrieveAllUsers());   //provide a link to the consumer of the API to inform on how to retrieve all users
			entityModel.add(link.withRel("all-users"));
			
			return entityModel;
			
		}
		
		@PostMapping("/jpa/users")
		//the validations defined for the User object will be automatically invoked
		public ResponseEntity<User> createUser(@Valid @RequestBody User user) {    //By using ResponseEntity we get status 201 Created back
			User savedUser=userRepository.save(user);
			//return also the URI(location) of the user created e.g: /users/4 . The user can then type the Uri and get the details of the newly created user back
			//Whenever we want to write a URL of a created resource , we have to use the Location HTTP header
			URI location= ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
			return ResponseEntity.created(location).build();
			
			
		}
		
		
		@DeleteMapping(path="jpa/users/{id}")
		public void deleteUser(@PathVariable int id){
			
			userRepository.deleteById(id);
		
			
		}
		
		//retrieve all posts of a user
		@GetMapping(path="jpa/users/{id}/posts")
		public List<Post> retrievePostsForAUser(@PathVariable int id){		  
		   //we have to find the user first
            Optional<User> user=userRepository.findById(id);
			
			if (user.isEmpty())
				throw new UserNotFoundException("id:"+id);  //we want to return status 404 and a relevant message
	      
			return user.get().getPosts();

	}
		
		//create a new Post for a specific user
		@PostMapping(path="jpa/users/{id}/posts")
		public ResponseEntity<Post> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post){		  
		   //we have to find the user first
            Optional<User> user=userRepository.findById(id);
			
			if (user.isEmpty())
				throw new UserNotFoundException("id:"+id);  //we want to return status 404 and a relevant message
	      
			post.setUser(user.get()); //set the user of this post!
			Post savedPost=postRepository.save(post);   //save the new post.
			URI location= ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPost.getId()).toUri(); //build the URI for the ne post
			return ResponseEntity.created(location).build();

	}
		
		//retrieve the details of a specific post
		
		@GetMapping(path="/jpa/users/{user_id}/posts/{id}")
		public EntityModel<Post> retrievePost(@PathVariable int user_id,@PathVariable int id){
			//check if user exists
			Optional<User> user=userRepository.findById(user_id);
			
			if (user.isEmpty())
				throw new UserNotFoundException("id:"+user_id);  //we want to return status 404 and a relevant message
			//check is post exists
			Optional<Post> post=postRepository.findById(id);
			User userOfPost= post.get().getUser();  //get the User of this post
			
			if (post.isEmpty() || userOfPost.getId()!=user_id) { //if the post for this specified user does not exist
				
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Post with this id not found for thi user.");
			}
			
			
			EntityModel<Post> entityModel = EntityModel.of(post.get());
			
			//points to the method of the controller retrievePostsForAuser
			WebMvcLinkBuilder link =  linkTo(methodOn(this.getClass()).retrievePostsForAUser(user_id));   //provide a link to the consumer of the API to inform on how to retrieve all posts of a user
			entityModel.add(link.withRel("all-posts-from-user"));
			
			return entityModel;
			
		}
		
		//Delete a post
		
		@DeleteMapping(path="jpa/users/{user_id}/posts/{id}")
		public void deletePost(@PathVariable int user_id,@PathVariable int id){
					
			 Optional<Post> post = postRepository.findById(id);
			 User userOfPost= post.get().getUser();
			 
		        if(!post.isPresent() || userOfPost.getId()!=user_id){
		            
		            
		            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Post with this id not found for the user.");	
		        }

		       postRepository.deleteById(id);
	          
		        
			
		}
		
		
		
}


