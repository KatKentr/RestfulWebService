package com.springboot.rest.webservices.socialmediaapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.rest.webservices.socialmediaapp.model.Post;
import com.springboot.rest.webservices.socialmediaapp.model.User;

//Repository to talk to the database
public interface PostRepository extends JpaRepository<Post,Integer>{

}
