package com.springboot.rest.webservices.socialmediaapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.springboot.rest.webservices.socialmediaapp.model.Comment;
import com.springboot.rest.webservices.socialmediaapp.model.Post;
import com.springboot.rest.webservices.socialmediaapp.model.User;
import com.springboot.rest.webservices.socialmediaapp.repository.CommentRepository;
import com.springboot.rest.webservices.socialmediaapp.repository.PostRepository;
import com.springboot.rest.webservices.socialmediaapp.repository.UserRepository;

@Service
public class CommentService {
	
	private CommentRepository commentRepository;
	private UserService userService;     //related to the userService. Maybe to be avoided? Use only methods of the repository?
	private PostRepository postRepository;
	private UserRepository userRepository;
	private AuthService authService;

	public CommentService(CommentRepository commentRepository,UserService userService, PostRepository postRepository,AuthService authService,UserRepository userRepository) {
		super();
		this.commentRepository = commentRepository;
		this.userService = userService;
		this.postRepository=postRepository;
		this.authService=authService;
		this.userRepository=userRepository;
	}
	
	public List<Comment> getCommentsFromUser(int UserId){		//get all comments of a user
		//we have to find the user first		
        Optional<User> user=userRepository.findById(UserId);       //Use the method of the UserRepository findByID instead?
        
		return user.get().getComments();   //return user's posts
		
	}
	
	
	public List<Comment> getCommentsFromPost(int postId){		//get all comments of a post
		
		//check is post exists
		Optional<Post> post=postRepository.findById(postId);
        if (post.isEmpty()) { //if the post does not exist
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Post with this id not ");
		}
		
		return post.get().getComments();
	}
	
	
	public Comment saveNewComment(Comment comment, int postId) {  //save a new comment
		
		//relate the new comment to a user and to a post
		User commentUser=authService.getCurrentUser();
    
				
		Optional<Post> post=postRepository.findById(postId);
        if (post.isEmpty()) { //if the post does not exist
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Post with this id not ");
		}
        
        comment.setUser(commentUser);
        comment.setPost(post.get());
        Comment newComment=commentRepository.save(comment);
        
        return newComment;
				
	}

}
