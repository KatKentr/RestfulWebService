package com.springboot.rest.webservices.socialmediaapp.controller;


import com.springboot.rest.webservices.socialmediaapp.constants.ApiRoutes;
import com.springboot.rest.webservices.socialmediaapp.jwt.JwtTokenUtil;
import com.springboot.rest.webservices.socialmediaapp.model.Role;
import com.springboot.rest.webservices.socialmediaapp.model.User;
import com.springboot.rest.webservices.socialmediaapp.service.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.config.http.MatcherType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest               //Working with Junit4 here
@RunWith(SpringRunner.class)
@WebMvcTest(UserControllerJPA.class)    //instantiate only one controller
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;


    private User user1,user2;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtTokenUtil jwtDecoder;

    @Before
    public void init(){

        Set<Role> roles=new HashSet<>();       //set the roles
        Role role1=new Role("ROLE_USER");
        role1.setId(1);
        Role role2=new Role("ROLE_ADMIN");
        role2.setId(2);
        roles.add(role1);
        roles.add(role2);

        user1=new User();
        user1.setUsername("Katerina");
        user1.setDate(LocalDate.of( 2018 , Month.JANUARY , 23));
        user1.setEmail("katerina@example.com");
        user1.setPassword("1234");
        user1.addRole(role1);
        user1.addRole(role2);
        user1.setId(1);

        user2 = new User();
        user2.setUsername("Nene");
        user2.setDate(LocalDate.of( 2018 , Month.JANUARY , 23));
        user2.setEmail("nene@example.com");
        user2.setPassword("1234");
        user2.addRole(role2);
        user2.setId(2);
        //System.out.println(user2.getId());
    }




    @Test
    @DisplayName("Should return all users, when an authenticated user requests the api")
    @WithMockUser(username = "NewAdminUser",password="1234", authorities = { "ROLE_USER", "ROLE_ADMIN" })  //represents an authenticated user
    public void retreiveAllUsersshouldReturnAllUsers() throws Exception {
        ArrayList<User> users= new ArrayList<>();
        users.add(user1);
        users.add(user2);

        users.forEach(m-> System.out.println(m.getRoles()));

        when(userService.getAllUsers()).thenReturn(users);


        this.mockMvc.perform(get(ApiRoutes.User.GET_ALL)
                        //.with(jwt())
                  .contentType(MediaType.APPLICATION_JSON))
                  .andDo(print())
                  .andExpect(status().isOk())
                   .andExpect(jsonPath("$", hasSize(users.size())));
    }





}
