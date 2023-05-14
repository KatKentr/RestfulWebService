package com.springboot.rest.webservices.socialmediaapp.repository;

import com.springboot.rest.webservices.socialmediaapp.model.Comment;
import com.springboot.rest.webservices.socialmediaapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
	
	Optional<Role> findByName(String name);

}
