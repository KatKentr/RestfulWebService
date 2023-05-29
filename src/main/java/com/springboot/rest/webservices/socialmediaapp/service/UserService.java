package com.springboot.rest.webservices.socialmediaapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.springboot.rest.webservices.socialmediaapp.exception.UserNotFoundException;
import com.springboot.rest.webservices.socialmediaapp.model.User;
import com.springboot.rest.webservices.socialmediaapp.repository.UserRepository;

//Would it be beneficial to make the UserService an interface and provide implementation(s) for the Interface?
@Service
public class UserService {
	
	UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {  //constructor-based injection
		
		this.userRepository=userRepository;
			
	}
	
	
	public List<User> getAllUsers() {     //get all users
		
		return userRepository.findAll();
	}
	
	
	public Optional<User> getUserById(int id) {    //return the user, if the user exists
		
		Optional<User> user=userRepository.findById(id);
		
		if (user.isEmpty())
			throw new UserNotFoundException("id: "+id);
	
	   return user;	
    }
	
	
	public void deleteUser(int id) {

		Optional<User> user=userRepository.findById(id);

		if (user.isEmpty())
			throw new UserNotFoundException("id: "+id);
		
		userRepository.deleteById(id);
	}
	
	                                                   

	
	

}
