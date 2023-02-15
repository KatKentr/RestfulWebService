package com.springboot.rest.webservices.restfulwebservices.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

//DAO Service: Data Access object

@Component
public class UserDaoService {
	
	//implement method to retrieve all Users: List<User> findAll()
	//public User save(User user)
	//public User findOne(int id)
	
	private static List<User> users = new ArrayList<>();
	private static int usersCount=0;
	
	static {      //whenever the server restarts we only have these three users
		
		users.add(new User(++usersCount,"Adam",LocalDate.now().minusYears(30)));
		users.add(new User(++usersCount,"Adam",LocalDate.now().minusYears(25)));
		users.add(new User(++usersCount,"Jim",LocalDate.now().minusYears(20)));
	}
	
	
	public List<User> findAll() {
		
		return users;
	}
	
	
	public User findOne(int id) {
		Predicate<? super User> predicate=user -> user.getId()==(id); //condition
		return users.stream().filter(predicate).findFirst().orElse(null);
		
	}
	
	
	public User save(User user) {  //add a new user
		user.setId(++usersCount);
		users.add(user);
		return user;
	}


	public void deleteById(int id) {
		Predicate<? super User> predicate=user -> user.getId()==(id); //condition
		users.removeIf(predicate);
	}
	
	
	
}
