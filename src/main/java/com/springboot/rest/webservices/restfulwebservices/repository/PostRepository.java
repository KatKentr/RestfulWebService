package com.springboot.rest.webservices.restfulwebservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.rest.webservices.restfulwebservices.model.Post;
import com.springboot.rest.webservices.restfulwebservices.model.User;

//Repository to talk to the database
public interface PostRepository extends JpaRepository<Post,Integer>{

}
