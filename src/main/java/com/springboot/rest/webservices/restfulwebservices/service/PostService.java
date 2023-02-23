package com.springboot.rest.webservices.restfulwebservices.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.springboot.rest.webservices.restfulwebservices.model.User;
import com.springboot.rest.webservices.restfulwebservices.model.Post;
import com.springboot.rest.webservices.restfulwebservices.model.UserNotFoundException;
import com.springboot.rest.webservices.restfulwebservices.repository.PostRepository;
import com.springboot.rest.webservices.restfulwebservices.repository.UserRepository;

@Service
public class PostService {
	
	PostRepository postRepository;
	UserRepository userRepository;
	
	UserService userService;
	
	
	public PostService(PostRepository postRepository,UserRepository userRepository,UserService userService) {
		this.postRepository=postRepository;
		this.userRepository=userRepository;
		
		this.userService=userService;
	}
	
	public List<Post> getPostsFromUser(int UserId){		//get all posts of a user
		//we have to find the user first		
        Optional<User> user=userService.getUserById(UserId);
        
		return user.get().getPosts();   //return user's posts
		
	}
	
	//Would this approach be more correct? method arguments: int userId, String description, instantiate the new Post object and then save it?
	public Post saveNewPost(int UserId, Post post) {                //add a new post for a user
	    //find the user
		Optional<User> user=userService.getUserById(UserId);
		post.setUser(user.get());   //relate the post to the user
		Post newPost= postRepository.save(post);   //save the new post.
		return newPost;
						
	}
	
	public Optional<Post> getPostDetails(int userId,int postId) {    //retrieve a post
		//find the user
		Optional<User> user=userService.getUserById(userId);
		//check is post exists
		Optional<Post> post=postRepository.findById(postId);
		User userOfPost= post.get().getUser();  //get the User of this post
		
		if (post.isEmpty() || userOfPost.getId()!=userId) { //if the post for this specified user does not exist
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Post with this id not found for this user.");
		}
		
		return post;
					
	}
	
	public void deletePostOfUserById(int userId, int postId) {  //delete a user's post
		
		Optional<Post> post=getPostDetails(userId,postId);
		postRepository.deleteById(postId);
		
	}
	
	
	
	
	
	

}
