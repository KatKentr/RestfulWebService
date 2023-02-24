package com.springboot.rest.webservices.restfulwebservices.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.springboot.rest.webservices.restfulwebservices.model.Comment;
import com.springboot.rest.webservices.restfulwebservices.model.Post;
import com.springboot.rest.webservices.restfulwebservices.model.User;
import com.springboot.rest.webservices.restfulwebservices.repository.CommentRepository;
import com.springboot.rest.webservices.restfulwebservices.repository.PostRepository;
import com.springboot.rest.webservices.restfulwebservices.repository.UserRepository;

@Service
public class CommentService {
	
	private CommentRepository commentRepository;
	private UserService userService;     //related to the userService. Maybe to be avoided? Use only methods of the repository?
	private PostRepository postRepository;
	private UserRepository userRepository;

	public CommentService(CommentRepository commentRepository,UserService userService, PostRepository postRepository) {
		super();
		this.commentRepository = commentRepository;
		this.userService = userService;
		this.postRepository=postRepository;
	}
	
	public List<Comment> getCommentsFromUser(int UserId){		//get all comments of a user
		//we have to find the user first		
        Optional<User> user=userService.getUserById(UserId);       //Use the method of the UserRepository findByID instead?
        
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
	
	
	public Comment saveNewComment(Comment comment, int userId, int postId) {  //save a new comment
		
		//relate the new comment to a user and to a post
		Optional<User> commentUser=userRepository.findById(userId);
        if (commentUser.isEmpty()) { //if the post does not exist
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User with this id not ");
		}
				
		Optional<Post> post=postRepository.findById(postId);
        if (post.isEmpty()) { //if the post does not exist
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Post with this id not ");
		}
        
        comment.setUser(commentUser.get());
        comment.setPost(post.get());
        Comment newComment=commentRepository.save(comment);
        
        return newComment;
				
	}

}
