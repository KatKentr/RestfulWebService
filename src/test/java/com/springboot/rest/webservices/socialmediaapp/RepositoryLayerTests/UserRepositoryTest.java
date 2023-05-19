

package com.springboot.rest.webservices.socialmediaapp.RepositoryLayerTests;

import com.springboot.rest.webservices.socialmediaapp.model.Role;
import com.springboot.rest.webservices.socialmediaapp.model.User;
import com.springboot.rest.webservices.socialmediaapp.repository.RoleRepository;
import com.springboot.rest.webservices.socialmediaapp.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(SpringRunner.class)
//@SpringBootTest(classes = SocialMediaApplication.class)
@DataJpaTest
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	private User user;
	
	
	@Before
	public void setUp() {
		 //initialize a valid user
		user=new User();               //Remember: id is autogenerated
		user.setUsername("Katerina");
		user.setDate(LocalDate.of( 2018 , Month.JANUARY , 23));
		user.setEmail("katerina@example.com");
		user.setPassword("1234");
        //set roles for the user
		Set<Role> roles=new HashSet<>();
		roles.add(new Role("ROLE_USER"));
		roles.add(new Role("ROLE_ADMIN"));
		roleRepository.saveAll(roles);
//        Role roleUser=new Role("ROLE_USER");
//        Role roleAdmin=new Role("ROLE_ADMIN");
        user.addRole(roleRepository.findByName("ROLE_USER").get());
        user.addRole(roleRepository.findByName("ROLE_ADMIN").get());
		System.out.println("BeforeEach");
	     	
	}

	
	//test case for saving a user
	@Test
	public void saveUserTest() {
		
		 userRepository.save(user);
		 System.out.println("id of tet user is: "+user.getId());
	     User fetchedUser = userRepository.findById(user.getId()).get();
	     assertEquals(user.getId(), fetchedUser.getId());
         assertEquals(fetchedUser.getRoles().size(),2);
			
	}
	
	//test case to retrieve all users that are stored
	@Test
	public void findAllUsersTest() {
		User user2 = new User();
		user.setUsername("Nene");
		user.setDate(LocalDate.of( 2018 , Month.JANUARY , 23));
		user.setEmail("nene@example.com");
		user.setPassword("1234");
		user.addRole(roleRepository.findByName("ROLE_USER").get());
	     userRepository.save(user);
	     userRepository.save(user2);
	     List<User> userList = (List<User>)userRepository.findAll();
	     assertEquals(user2.getUsername(), userList.get(1).getUsername());
         assertEquals(userList.size(),2);
			
	}
	
	
	//test case to retrieve a user by id
	
	@Test
	public void findUserbyIdTest_exists() {
		User newUser=userRepository.save(user);
		 Optional<User> opt =  userRepository.findById(newUser.getId());
	     assertEquals(newUser.getId(), opt.get().getId());
	     assertEquals(newUser.getEmail(), opt.get().getEmail());
		
	}
	

	
	//test case for deleting a user by id
	@Test
	public void deleteUserByIdTest() {

		userRepository.save(user);
		System.out.println("id of tet user is: "+user.getId());
		userRepository.deleteById(user.getId());
		Optional optional = userRepository.findById(user.getId());
		assertEquals(Optional.empty(), optional);
		
	}
	
	
//	 Optional<User> findByEmail(String email);
//	    Optional<User> findByUsernameOrEmail(String username, String email);
//	    Optional<User> findByUsername(String username);
//	    Boolean existsByUsername(String username);
//	    Boolean existsByEmail(String email);
	

	@Test
	public void findByEmailTest_exists() {
		 userRepository.save(user);
		 Optional<User> opt =  userRepository.findByEmail(user.getEmail());
	     assertEquals(user.getEmail(), opt.get().getEmail());
	     assertEquals(user.getId(), opt.get().getId());
	}
	
	@Test
	public void findByEmailTest_notExists() {
		Optional<User> user = userRepository.findByEmail("notExisting@gmail.com");
		assertEquals(Optional.empty(), user);
	}
	
	
	@Test
	public void existsByUsernameTest_exists() {
		 userRepository.save(user);
		 boolean flag=userRepository.existsByUsername(user.getUsername());
		 assertTrue(flag);
		
	}
	
	@Test
	public void existsByUsernameTest_notExists() {
		
		 boolean flag=userRepository.existsByUsername("Foufoutos");
		 assertEquals(false,flag);
				
	}
	
	@Test
	public void existsByEmailTest_Exists() {
		 userRepository.save(user);
		 boolean flag=userRepository.existsByEmail(user.getEmail());
		 assertTrue(flag);
		
	}
	
	@Test
	public void existsByEmailTest_NotExists() {
		
		 boolean flag=userRepository.existsByEmail("Foufoutos@example.com");
		 assertEquals(false,flag);
				
	}
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
