//package com.springboot.rest.webservices.restfulwebservices;
//
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//
//import org.springframework.test.web.servlet.ResultActions;
//
//import org.springframework.http.MediaType;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.springboot.rest.webservices.restfulwebservices.jpa.UserRepository;
//import com.springboot.rest.webservices.restfulwebservices.user.User;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.BDDMockito.willDoNothing;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.hamcrest.Matchers.*;
//
//
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//public class UserControllerTests {
//	
//	 @Autowired
//	 private MockMvc mockMvc;
//
//	  @Autowired
//	  private UserRepository userRepository;
//
//	  @Autowired
//	  private ObjectMapper objectMapper;
//	  
//	  
//	  User user1=new User(1, "Katerina",LocalDate.now().minusYears(30));
//	  User user2=new User(2, "Petran",LocalDate.now().minusYears(20));
//	  User user3=new User(3, "Ruby",LocalDate.now().minusYears(20));
//	  
//	    
//	  @AfterAll
//	    void setup(){
//	        userRepository.deleteAll();
//	    }
//	  
//	  
//	  @Test
//	  public void retrieveAllUsers_sucess() throws Exception {
//		  
//		  List<User> users= new ArrayList<>();
//		  users.add(user1);
//		  users.add(user2);
//		  users.add(user3);
//		  userRepository.saveAll(users);
//		  
//		  // when -  action or the behaviour that we are going test
//	        ResultActions response = mockMvc.perform(get("/api/employees"));
//
//	        // then - verify the output
//	        response.andExpect(status().isOk())
//	                .andDo(print())
//	                .andExpect(jsonPath("$.size()",
//	                        is(users.size())));
//
//		  
//	  }
//	  
//	  
//	  
////	  @Test
////	  public void createAUser() throws Exception{
////		  
////		  User user=User.builder()
////				  .name("Ravii")
////				  .date(LocalDate.now().minusYears(30))
////				  .build();
////		  
////	  }
//  
//	    
//
//}
