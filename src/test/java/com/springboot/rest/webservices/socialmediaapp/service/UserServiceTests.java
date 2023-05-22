package com.springboot.rest.webservices.socialmediaapp.service;


import com.springboot.rest.webservices.socialmediaapp.TestUtils;
import com.springboot.rest.webservices.socialmediaapp.exception.UserNotFoundException;
import com.springboot.rest.webservices.socialmediaapp.model.Role;
import com.springboot.rest.webservices.socialmediaapp.model.User;
import com.springboot.rest.webservices.socialmediaapp.repository.RoleRepository;
import com.springboot.rest.webservices.socialmediaapp.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.ArgumentCaptor;


@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;


    @InjectMocks
    private UserService userService;

    private User user,user2;


    private TestUtils testUtils;

    @BeforeEach
    public void setUp(){

        testUtils=new TestUtils();  //instantiate testUtils

        user=testUtils.createUserWithBothRoles();   //user1 has authorities: ROLE_USER, ROLE_ADMIN
        user2=testUtils.createUserwithRoleUser();   //user2 has authorities: ROLE_USER

        //System.out.println("BeforeEach");
    }


    @Test
    public void getAllUsersTest(){
        when(userRepository.findAll()).thenReturn(List.of(user,user2));
        List<User> users=userService.getAllUsers();
        assertEquals(users.size(),2);

    }

    @Test
    public void getUserByIdTest_userExists(){

        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        Optional<User> retrievedUser=userService.getUserById(user.getId());
        assertEquals(user.getId(),retrievedUser.get().getId());
        assertEquals(user.getEmail(),retrievedUser.get().getEmail());
        assertEquals(user.getUsername(),retrievedUser.get().getUsername());
        assertEquals(user.getDate(),retrievedUser.get().getDate());
        assertEquals(user.getRoles().size(),retrievedUser.get().getRoles().size());

    }

    @Test
    public void getUserByIdTest_userNotExists(){
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());  //in case the user with thiss id doe not eits
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(user.getId());
        });
        assertTrue(exception.getMessage().contains("User with " +"id: "+user.getId()+ " not found"));

    }


    //TO DO:
    @Test
    public void deleteUserTest(){

//       when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));

 //Following approach, adapted from: https://github.com/SalithaUCSC/spring-boot-testing/blob/main/src/test/java/com/rest/order/OrderServiceUnitTest.java . Not clear though

        userService.deleteUser(user.getId());
        verify(userRepository, times(1)).deleteById(user.getId());
        ArgumentCaptor<Integer> userArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(userRepository).deleteById(userArgumentCaptor.capture());
        int userIdDeleted = userArgumentCaptor.getValue();
        assertNotNull(userIdDeleted);
        assertEquals(1, userIdDeleted);

    }

}
