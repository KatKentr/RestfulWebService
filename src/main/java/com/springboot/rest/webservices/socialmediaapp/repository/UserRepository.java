package com.springboot.rest.webservices.socialmediaapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.rest.webservices.socialmediaapp.model.User;

//Repository to talk to the database
public interface UserRepository extends JpaRepository<User,Integer>{
	
	 Optional<User> findByEmail(String email);
	    Optional<User> findByUsernameOrEmail(String username, String email);
	    Optional<User> findByUsername(String username);
	    Boolean existsByUsername(String username);
	    Boolean existsByEmail(String email);

}
