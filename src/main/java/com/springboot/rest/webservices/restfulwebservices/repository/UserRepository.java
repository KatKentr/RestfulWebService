package com.springboot.rest.webservices.restfulwebservices.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.rest.webservices.restfulwebservices.model.User;

//Repository to talk to the database
public interface UserRepository extends JpaRepository<User,Integer>{
	
	 Optional<User> findByEmail(String email);
	    Optional<User> findByUsernameOrEmail(String name, String email);
	    Optional<User> findByUsername(String name);
	    Boolean existsByUsername(String name);
	    Boolean existsByEmail(String email);

}
