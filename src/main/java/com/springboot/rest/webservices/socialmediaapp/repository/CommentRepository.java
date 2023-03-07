package com.springboot.rest.webservices.socialmediaapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.springboot.rest.webservices.socialmediaapp.model.Comment;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
	
	

}
