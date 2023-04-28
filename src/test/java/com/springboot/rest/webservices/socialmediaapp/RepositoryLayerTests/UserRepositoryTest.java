package com.springboot.rest.webservices.socialmediaapp.RepositoryLayerTests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.springboot.rest.webservices.socialmediaapp.SocialMediaApplication;
import com.springboot.rest.webservices.socialmediaapp.model.User;
import com.springboot.rest.webservices.socialmediaapp.repository.UserRepository;


//@ExtendWith(SpringExtension.class)
//@DataJpaTest
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SocialMediaApplication.class)
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;
	private User user;
	
	
	@BeforeEach
	public void setUp() {
		 //initialize a valid user
		 user = new User(1, "Katerina",LocalDate.of( 2018 , Month.JANUARY , 23),"katerina@example.com","1234","user_role");
		 System.out.println("BeforeEach");
	     	
	}
	
	
	@AfterEach
    public void tearDown() {
        userRepository.deleteAll();
        user = null;
        System.out.println("AfterEach");
    }
	
	//test case for saving a user
	@Test
	public void saveUserTest() {
		
		 userRepository.save(user);
		 System.out.println("id of tet user is: "+user.getId());
	     User fetchedUser = userRepository.findById(user.getId()).get();
	     assertEquals(1, fetchedUser.getId());
			
	}
	
	//test case to retrieve all users that are stored
	@Test
	public void findAllUsersTest() {
		
		 User user1 = new User(2, "Katerina",LocalDate.of( 2018 , Month.JANUARY , 23),"katerina@example.com","1234","user_role");
		 User user2 = new User(3, "Nina",LocalDate.of( 2018 , Month.JANUARY , 23),"nina@example.com","1234","user_role");
	     userRepository.save(user1);
	     userRepository.save(user2);
	     List<User> userList = (List<User>)userRepository.findAll();
	     assertEquals("Nina", userList.get(1).getName());
			
	}
	
	
	//test case to retrieve a user by id
	
	@Test
	public void findUserbyId_success() {
		User newUser=userRepository.save(user);
		 Optional<User> opt =  userRepository.findById(newUser.getId());
	     assertEquals(newUser.getId(), opt.get().getId());
	     assertEquals(newUser.getName(), opt.get().getName());
		
	}
	

	
	//test case for deleting a user by id
	@Test
	public void deleteUserById() {
		User user1 = new User(5, "Nene",LocalDate.of( 2018 , Month.JANUARY , 23),"nnnn@example.com","1234","user_role");
		  userRepository.save(user1);
		  System.out.println("id of tet user is: "+user1.getId());
	      userRepository.deleteById(user1.getId());
	      Optional optional = userRepository.findById(user1.getId());
	      assertEquals(Optional.empty(), optional);
		
	}
	
	
//	 Optional<User> findByEmail(String email);
//	    Optional<User> findByUsernameOrEmail(String username, String email);
//	    Optional<User> findByUsername(String username);
//	    Boolean existsByUsername(String username);
//	    Boolean existsByEmail(String email);
	
	//when we add the folllowing tests, the saveUser and the deleteUser test fail.
	
	@Test
	public void findByEmail_success() {
		User newUser=userRepository.save(user);
		 Optional<User> opt =  userRepository.findByEmail(user.getEmail());
	     assertEquals(newUser.getEmail(), opt.get().getEmail());
	     assertEquals(newUser.getId(), opt.get().getId());		
	}
	
	@Test
	public void findByEmail_failed() {
		Optional<User> user = userRepository.findByEmail("notExisting@gmail.com");
		assertEquals(Optional.empty(), user);
	}
	
	
	@Test
	public void existsByUsername_success() {
		 User newUser=userRepository.save(user);
		 boolean flag=userRepository.existsByUsername(newUser.getName());
		 assertTrue(flag);
		
	}
	
	@Test
	public void existsByUsername_failed() {
		
		 boolean flag=userRepository.existsByUsername("Foufoutos");
		 assertEquals(false,flag);
				
	}
	
	@Test
	public void existsByEmail_success() {
		 User newUser=userRepository.save(user);
		 boolean flag=userRepository.existsByEmail(newUser.getEmail());
		 assertTrue(flag);
		
	}
	
	@Test
	public void existsByEmail_failed() {
		
		 boolean flag=userRepository.existsByEmail("Foufoutos@example.com");
		 assertEquals(false,flag);
				
	}
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
