package com.springboot.rest.webservices.socialmediaapp.controller;


import com.springboot.rest.webservices.socialmediaapp.TestUtils;
import com.springboot.rest.webservices.socialmediaapp.constants.ApiRoutes;
import com.springboot.rest.webservices.socialmediaapp.exception.UserNotFoundException;
import com.springboot.rest.webservices.socialmediaapp.jwt.JwtTokenUtil;
import com.springboot.rest.webservices.socialmediaapp.model.Role;
import com.springboot.rest.webservices.socialmediaapp.model.User;
import com.springboot.rest.webservices.socialmediaapp.service.UserService;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.http.MatcherType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static javax.management.Query.value;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

//@SpringBootTest1
//@RunWith(SpringRunner.class)
@WebMvcTest(UserControllerJPA.class)    //instantiate only one controller
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;


    private User user1,user2;

    private TestUtils testUtils;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtTokenUtil jwtDecoder;

    @BeforeEach
    public void init(){

        testUtils=new TestUtils();  //instantiate testUtils


        user1=testUtils.createUserWithBothRoles();   //user1 has authorities: ROLE_USER, ROLE_ADMIN
        user2=testUtils.createUserwithRoleUser();   //user2 has authorities: ROLE_USER


    }




    @Test
    @DisplayName("Should return all users, when an authenticated user requests the api")
    @WithMockUser(username = "NewAdminUser",password="1234", authorities = { "ROLE_USER", "ROLE_ADMIN" })  //represents an authenticated user
    public void retreiveAllUsersshouldReturnAllUsers() throws Exception {
        ArrayList<User> users= new ArrayList<>();
        users.add(user1);
        users.add(user2);

        //users.forEach(m-> System.out.println(m.getRoles()));

        when(userService.getAllUsers()).thenReturn(users);


        this.mockMvc.perform(get(ApiRoutes.User.GET_ALL)
                        //.with(jwt())
                  .contentType(MediaType.APPLICATION_JSON))
                  .andDo(print())
                  .andExpect(status().isOk())
                   .andExpect(jsonPath("$", hasSize(users.size())));
    }


    @Test
    @DisplayName("Should return the user by id,when the user exists and when an authenticated user requests the api")
    @WithMockUser(username = "NewAdminUser",password="1234", authorities = { "ROLE_USER", "ROLE_ADMIN" })  //represents an authenticated user
    public void retrieveUserByIdWhenUserExists() throws Exception{

       //when(userService.getUserById(user1.getId()).thenReturn(Optional.ofNullable(user1)));   //encountered error due to Optional

      doReturn(Optional.of(user1)).when(userService).getUserById(user1.getId());  //It worked!!

     this.mockMvc.perform(get(ApiRoutes.User.GET_BY_ID,Integer.toString(user1.getId())))   //we pass user's id as path variable

                  .andDo(print())
                 .andExpect(status().isOk())
             .andExpect(MockMvcResultMatchers.content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.username",equalTo(user1.getUsername())))
                 .andExpect(jsonPath("$.email", equalTo(user1.getEmail())))
                    .andExpect(jsonPath("$._links.all-users.href", Matchers.is("http://"+ApiRoutes.LOCAL_HOST+ApiRoutes.User.GET_ALL))); //verify that the link provided, as part of the json reponse, to retrieve all users is also present!
    }

    //TODO: tests for unthaunticated users?


    @Test
    @DisplayName("Should return the userNotFoundException,when the user does not exists and an authenticated user requests the api")
    @WithMockUser(username = "NewAdminUser",password="1234", authorities = { "ROLE_USER", "ROLE_ADMIN" })
    public void retrieveUserByIdWhenUserNotExists() throws Exception{


        given(userService.getUserById(user1.getId()))
                .willThrow(new UserNotFoundException("id: "+user1.getId()));

        this.mockMvc.perform(get(ApiRoutes.User.GET_BY_ID,Integer.toString(user1.getId())))   //we pass user's id as path variable
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                //.andExpect(jsonPath("$.message", containsString("id: "+user1.getId())))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException))
                .andExpect(result ->jsonPath("$.message", containsString(result.getResolvedException()
                        .getMessage()+"id: "+user1.getId())));

    }


    //TODO: two test cases: is authenticated and authorized to delete a user and is unauthorized to delete a user

    @Test
    //@WithMockUser(authorities = "ROLE_ADMIN")
    @WithMockUser(username = "NewAdminUser",password="1234", roles= "USER")
    public void deleteUserByIdShouldReturn200() throws Exception {

        user1.getAuthorities().forEach(m ->System.out.println(m));

        //deleteUser method is void
        doNothing().when(userService).deleteUser(user1.getId());

        ResultActions response =this.mockMvc.perform(delete(ApiRoutes.User.GET_BY_ID,Integer.toString(user1.getId()))   //we pass user's id as path variable
                .with(csrf()) //provide a CSRF token for the request
                //.with(jwt())
                //.with(user("aDMIN").password("1234").roles("USER"))
                .contentType(MediaType.APPLICATION_JSON));

        response.andDo(print())
                .andExpect(status().isOk());

    }








}
