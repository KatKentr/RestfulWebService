package com.springboot.rest.webservices.socialmediaapp.RepositoryLayerTests;

import com.springboot.rest.webservices.socialmediaapp.SocialMediaApplication;
import com.springboot.rest.webservices.socialmediaapp.model.Post;
import com.springboot.rest.webservices.socialmediaapp.model.User;
import com.springboot.rest.webservices.socialmediaapp.repository.PostRepository;
import com.springboot.rest.webservices.socialmediaapp.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//refers to JUNIT4
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


//@ExtendWith(SpringExtension.class)
//@DataJpaTest
@RunWith(SpringRunner.class)
//@SpringBootTest(classes = SocialMediaApplication.class)
@DataJpaTest
public class PostRepositoryTest {
	
	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;
	private Post post;
	private User user;
	
	
	@Before
	public void setUp() {
		 //initialize a valid user
//		 user= new User(1, "Katerina",LocalDate.of( 2018 , Month.JANUARY , 23),"katerina@example.com","1234","user_role");
		 user=new User();
		 user.setName("Katerina");
		 user.setDate(LocalDate.of( 2018 , Month.JANUARY , 23));
		 user.setEmail("katerina@example.com");
		 user.setPassword("1234");
		 user.setRoles("user_role");
		 userRepository.save(user);
		 System.out.println("user saved");
		 //post=new Post(1,"description",user);
		post=new Post();
		post.setUser(user);
		post.setDescription("description");
		 System.out.println("BeforeEach");
	     	
	}
	
	
//	@After
//    public void tearDown() {
//
//
//		userRepository.deleteAll();
//		postRepository.deleteAll();
//        //user = null;
//        System.out.println("AfterEach");
//    }
	
	//test case for saving a user
	@Test
	public void savePostTest() {
		
		 postRepository.save(post);
		 System.out.println("id of the post is: "+post.getId());
	     Post fetchedPost= postRepository.findById(post.getId()).get();
	     assertEquals(post.getId(), fetchedPost.getId());
			
	}
	
	//test case to retrieve all users that are stored
	@Test
	public void findAllPostsTest() {

		 postRepository.save(post);
		 //Post post2=new Post(2,"description",user);
		 Post post2=new Post();
		 post2.setDescription("description22");
		 post2.setUser(user);
	     postRepository.save(post2);
		 List<Post> result = new ArrayList<>();
		 result.addAll(postRepository.findAll());
		 assertEquals(result.size(), 2);

	}
	
	
	//test case to retrieve a post by id
	
	@Test
	public void findPostbyIdTest_exists() {
		 postRepository.save(post);
		 Optional<Post> opt =  postRepository.findById(post.getId());
	     assertEquals(post.getId(), opt.get().getId());
	     assertEquals(post.getDescription(), opt.get().getDescription());
		
	}

	@Test
	public void findPostbyIdTest_notExists() {

		Optional<Post> opt =  postRepository.findById(133);
		assertEquals(Optional.empty(), opt);


	}
	

	
	//test case for deleting a post by id
	@Test
	public void deletePostByIdTest() {

		  postRepository.save(post);
		  System.out.println("id of post is: "+post.getId());
	      postRepository.deleteById(post.getId());
	      Optional optional = postRepository.findById(post.getId());
	      assertEquals(Optional.empty(), optional);
		
	}
	
	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
