package com.springboot.rest.webservices.socialmediaapp.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import jakarta.annotation.security.RolesAllowed;
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

import com.springboot.rest.webservices.socialmediaapp.constants.ApiRoutes;
import com.springboot.rest.webservices.socialmediaapp.exception.UserNotFoundException;
import com.springboot.rest.webservices.socialmediaapp.model.Post;
import com.springboot.rest.webservices.socialmediaapp.model.User;
import com.springboot.rest.webservices.socialmediaapp.repository.PostRepository;
import com.springboot.rest.webservices.socialmediaapp.repository.UserRepository;
import com.springboot.rest.webservices.socialmediaapp.service.PostService;
import com.springboot.rest.webservices.socialmediaapp.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserControllerJPA {

	private UserService userService;

	public UserControllerJPA(UserService userService) { // constructor-based Injection

		this.userService = userService;

	}

	@GetMapping(ApiRoutes.User.GET_ALL)
	@RolesAllowed("ROLE_USER")
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

	//TO DO: Only users with role ADMIN will be able to delete users
	@DeleteMapping(ApiRoutes.User.GET_BY_ID)
	public void deleteUser(@PathVariable int id) {

		userService.deleteUser(id);

	}

}
