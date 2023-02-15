package com.springboot.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	
	@GetMapping(path="/users/{id}")
	public User retrieveUser(@PathVariable int id){
		
		User user=service.findOne(id);
		
		if (user==null)
			throw new UserNotFoundException("id:"+id);  //we want to return status 404 and a relevant message
		
		return service.findOne(id);
		
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
	
	
	@DeleteMapping(path="/users/{id}")
	public void deleteUser(@PathVariable int id){
		
		service.deleteById(id);
	
		
	}

}
