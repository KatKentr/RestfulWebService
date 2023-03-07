package com.springboot.rest.webservices.restfulwebservices.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springboot.rest.webservices.restfulwebservices.constants.ApiRoutes;
import com.springboot.rest.webservices.restfulwebservices.model.Post;
import com.springboot.rest.webservices.restfulwebservices.model.User;
import com.springboot.rest.webservices.restfulwebservices.model.UserNotFoundException;
import com.springboot.rest.webservices.restfulwebservices.repository.PostRepository;
import com.springboot.rest.webservices.restfulwebservices.repository.UserRepository;
import com.springboot.rest.webservices.restfulwebservices.service.PostService;
import com.springboot.rest.webservices.restfulwebservices.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserControllerJPA {

	private UserService userService;

	public UserControllerJPA(UserService userService) { // constructor-based Injection

		this.userService = userService;

	}

	@GetMapping(ApiRoutes.User.GET_ALL)
	public List<User> retrieveAllUsers() {

		return userService.getAllUsers();
	}

	// we wrap the User class and create an EntityModel in order to use Spring
	// HATEOAS: Generate HAL responses with hyperlinks to resources
	@GetMapping(ApiRoutes.User.GET_BY_ID)
	public EntityModel<User> retrieveUser(@PathVariable int id) {

		Optional<User> user = userService.getUserById(id);
		EntityModel<User> entityModel = EntityModel.of(user.get());

		// points to the method of the controller retrieveAllUsers
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers()); // provide a link to the consumer of the API to inform on how to retrieve all users

		entityModel.add(link.withRel("all-users"));

		return entityModel;

	}

	@PostMapping("/jpa/users")
	// the validations defined for the User object will be automatically invoked
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) { // By using ResponseEntity we get status 201
																			// Created back

		User savedUser = userService.saveUser(user);
		// return also the URI(location) of the user created e.g: /users/4 . The user
		// can then type the Uri and get the details of the newly created user back
		// Whenever we want to write a URL of a created resource , we have to use the
		// Location HTTP header
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();

	}

	@DeleteMapping(path = "jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {

		userService.deleteUser(id);

	}

}
