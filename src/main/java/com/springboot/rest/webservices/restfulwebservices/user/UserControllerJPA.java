package com.springboot.rest.webservices.restfulwebservices.user;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

//@RestController
//public class UserControllerJPA {
//	
//	private UserRepository userRepository;
//	
//	public UserControllerJPA(UserRepository userRepoitory) { //constructor-based Injection
//		
//		this.userRepository=userRepository;
//	}
//	
//	@GetMapping(path= "/users")
//	public List<User> getUsers() {
//		
//		return userRepository.findAll();
//	}
//	
////	@GetMapping(path= "/users/{userId}")
////	public User getUser(@PathVariable Integer id) {
////		
////		Optional<User> user= UserRepository.findById(id);
////        //Spring wandelt das Objekt im Hintergrund automatisch in JSON um
////        if(user.isPresent()){
////            return user.get();
////        }
////        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User with this id not found.");
////    }
//	}
//	


