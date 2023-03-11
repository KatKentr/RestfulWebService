package com.springboot.rest.webservices.socialmediaapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.springboot.rest.webservices.socialmediaapp.exception.CommentNotFoundException;
import com.springboot.rest.webservices.socialmediaapp.exception.PostNotFoundException;
import com.springboot.rest.webservices.socialmediaapp.exception.UserNotFoundException;
import com.springboot.rest.webservices.socialmediaapp.model.Comment;
import com.springboot.rest.webservices.socialmediaapp.model.Post;
import com.springboot.rest.webservices.socialmediaapp.model.User;
import com.springboot.rest.webservices.socialmediaapp.repository.CommentRepository;
import com.springboot.rest.webservices.socialmediaapp.repository.PostRepository;
import com.springboot.rest.webservices.socialmediaapp.repository.UserRepository;

@Service
public class CommentService {
	
	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private UserRepository userRepository;
	private AuthService authService;

	public CommentService(CommentRepository commentRepository, PostRepository postRepository,AuthService authService,UserRepository userRepository) {
		super();
		this.commentRepository = commentRepository;
		this.postRepository=postRepository;
		this.authService=authService;
		this.userRepository=userRepository;
	}
	
	public List<Comment> getCommentsFromUser(int userId){		//get all comments of a user
		//we have to find the user first		
        Optional<User> user=userRepository.findById(userId);
        
        if (user.isEmpty()) {
        	
        	throw new UserNotFoundException("id: "+ userId);
        }
        
		return user.get().getComments();   //return user's comments
		
	}
	
	
	public List<Comment> getCommentsFromPost(int postId){		//get all comments of a post
		
		//check is post exists
		Optional<Post> post=postRepository.findById(postId);
        if (post.isEmpty()) { //if the post does not exist
			
			throw new PostNotFoundException("id: "+postId);
		}
		
		return post.get().getComments();
	}
	
	
	public Comment saveNewComment(Comment comment, int postId) {  //save a new comment
		
		//relate the new comment to the authenticated user and to a post
		User commentUser=authService.getCurrentUser();
    
				
		Optional<Post> post=postRepository.findById(postId);
        if (post.isEmpty()) { //if the post does not exist
			
			throw new PostNotFoundException("id: "+postId);
		}
        
        comment.setUser(commentUser);
        comment.setPost(post.get());
        Comment newComment=commentRepository.save(comment);
        
        return newComment;
				
	}

	public Optional<Comment> getCommentDetails(int commentId) {
		
		Optional<Comment> comm=commentRepository.findById(commentId);
		if (comm.isEmpty()) {  //if comment does not exist
			
			throw new CommentNotFoundException("id: "+commentId);
		}
		
		return comm;
	}

}
