package com.springboot.rest.webservices.socialmediaapp.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.springboot.rest.webservices.socialmediaapp.exception.UserNotFoundException;
import com.springboot.rest.webservices.socialmediaapp.model.User;
import com.springboot.rest.webservices.socialmediaapp.repository.UserRepository;

//Would it be beneficial to make the UserService an interface and provide implementation(s) for the Interface?
@Service
public class UserService {
	
	UserRepository userRepository;

	AuthService authService;
	
	public UserService(UserRepository userRepository, AuthService authService) {  //constructor-based injection
		
		this.userRepository=userRepository;
		this.authService=authService;
			
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


	public User follow(String usernameToFollow) throws UserNotFoundException {
		User user=authService.getCurrentUser();
		Optional<User> toFollowOptional=userRepository.findByUsername(usernameToFollow);  //else throw userNotFoundException
		User toFollow=toFollowOptional.orElseThrow(() -> new UserNotFoundException("username: "+usernameToFollow));
		user.addFollowing(toFollow);
		if (user.getFollowing().contains(toFollow)){

			return toFollow;
		} else {

			throw new RuntimeException();   //TODO: add relevant message somethig unexpected happen
		}

	}

	public void unfollow(Integer toUnfollowId) throws UserNotFoundException {

		User user=authService.getCurrentUser();
		Optional<User> toUnfollowOptional=userRepository.findById(toUnfollowId);  //else throw userNotFoundException
		User toUnfollow=toUnfollowOptional.orElseThrow(() -> new UserNotFoundException("id: "+toUnfollowId));
		if (user.getFollowing().contains(toUnfollow)) {
			user.removeFollowing(toUnfollow);
		}

	}

	public Set<User> getFollowersOfUser(Integer userId) throws UserNotFoundException {     //TODO: replace return type ith UserDto

		Optional<User> userOptional = userRepository.findById(userId);
		User user = userOptional.orElseThrow(() -> new UserNotFoundException("id: " + userId));

		return user.getFollowers();

	}

	public Set<User> getFollowingUsers(Integer userId) throws UserNotFoundException {

		Optional<User> userOptional = userRepository.findById(userId);
		User user = userOptional.orElseThrow(() -> new UserNotFoundException("id: " + userId));
		return user.getFollowing();

	}




	}
