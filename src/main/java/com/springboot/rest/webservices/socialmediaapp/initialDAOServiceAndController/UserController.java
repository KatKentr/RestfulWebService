package com.springboot.rest.webservices.socialmediaapp.initialDAOServiceAndController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springboot.rest.webservices.socialmediaapp.exception.UserNotFoundException;
import com.springboot.rest.webservices.socialmediaapp.model.User;

import jakarta.validation.Valid;

@RestController
public class UserController {
	
	private UserDaoService service;
	
	
	public UserController (UserDaoService service) { //constructor based injection
		this.service=service;
		
	}
		
	@GetMapping(path="/users")
	public List<User> retrieveAllUsers(){
		
		return service.findAll();
		
	}
	
	
	//we wrap the User class and create an EntityModel in order to use Spring HATEOAS: Generate HAL responses with hyperlinks to resources
	@GetMapping(path="/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id){
		
		User user=service.findOne(id);
		
		if (user==null)
			throw new UserNotFoundException("id:"+id);  //we want to return status 404 and a relevant message
		
		EntityModel<User> entityModel = EntityModel.of(user);
		
		//points to the method of the controller retrieveAllUsers
		WebMvcLinkBuilder link =  linkTo(methodOn(this.getClass()).retrieveAllUsers());   //provide a link to the consumer of the API to inform on how to retrieve all users
		entityModel.add(link.withRel("all-users"));
		
		return entityModel;
		
	}
	
	@PostMapping("/users")
	//the validations defined for the User object will be automatically invoked
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {    //By using ResponseEntity we get status 201 Created back
		User savedUser=service.save(user);
		//return also the URI(location) of the user created e.g: /users/4 . The user can then type the Uri and get the details of the newly created user back
		//Whenever we want to write a URL of a created resource , we have to use the Location HTTP header
		URI location= ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
		
		
	}
	
	//TO DO: Only users with role ADMIN will be able to delete users
	@DeleteMapping(path="/users/{id}")
	public void deleteUser(@PathVariable int id){
		
		service.deleteById(id);
	
		
	}

}
