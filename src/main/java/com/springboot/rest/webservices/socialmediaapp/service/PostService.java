package com.springboot.rest.webservices.socialmediaapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.springboot.rest.webservices.socialmediaapp.exception.PostNotFoundException;
import com.springboot.rest.webservices.socialmediaapp.exception.UserNotAllowedException;
import com.springboot.rest.webservices.socialmediaapp.exception.UserNotFoundException;
import com.springboot.rest.webservices.socialmediaapp.model.Post;
import com.springboot.rest.webservices.socialmediaapp.model.User;
import com.springboot.rest.webservices.socialmediaapp.repository.PostRepository;
import com.springboot.rest.webservices.socialmediaapp.repository.UserRepository;

@Service
public class PostService {
	
	PostRepository postRepository;
	UserRepository userRepository;
	
	UserService userService;
	AuthService authService;
	
	
	public PostService(PostRepository postRepository,UserRepository userRepository,UserService userService,AuthService authService) {
		this.postRepository=postRepository;
		this.userRepository=userRepository;
		
		this.userService=userService;
		this.authService=authService;
	}
	
	public List<Post> getPostsFromUser(int UserId){		//get all posts of a user
		//we have to find the user first		
		var user=userRepository.findById(UserId).orElseThrow(()-> new UserNotFoundException("id: "+UserId));
        
		return user.getPosts();   //return user's posts
		
	}
	
	//Would this approach be more correct? method arguments: int userId, String description, instantiate the new Post object and then save it?
	public Post saveNewPost(Post post) {                //add a new post for a user
	    //find the user
		System.out.println("inside saveNewPost method");
		User user=authService.getCurrentUser();
		System.out.println("got current user" +user.getEmail());
		post.setUser(user);   //relate the post to the user
		Post newPost= postRepository.save(post);   //save the new post.
		return newPost;
						
	}
	
	
	public List<Post> getAllPosts() {  //get all posts
		
		return postRepository.findAll();
		
	}
	
	
	public Optional<Post> getPostDetails(int postId) {    //retrieve a post
	
		//check is post exists
		Optional<Post> post=postRepository.findById(postId);
		
		if (post.isEmpty()) { //if the post does not exist
			
			throw new PostNotFoundException("id: "+postId);
		}
		
		return post;
					
	}
	
	//TO DO: differentiate between roles: ADMIN and USER_ROLE
	public void deletePostOfUserById(int postId) {  //delete a user's post
		
		Optional<Post> post=getPostDetails(postId);
        if (post.isEmpty()) { //if the post does not exist
			
			throw new PostNotFoundException("id: "+postId);
		}
        
		User userOfPost=post.get().getUser();
		User currentUser=authService.getCurrentUser();
		if (userOfPost.getId() != currentUser.getId()){   //users are able to delete only their own posts
			
			throw new UserNotAllowedException();
			
		}
		
		postRepository.deleteById(postId);
		
	}


	
	
	
	
	
	

}
