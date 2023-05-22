package com.springboot.rest.webservices.socialmediaapp;

import com.springboot.rest.webservices.socialmediaapp.model.Role;
import com.springboot.rest.webservices.socialmediaapp.model.User;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TestUtils {

    private User user;

    private Role userRole;
    private Role adminRole;



    public TestUtils(){

        //initialize set of roles
        Set<Role> roles=new HashSet<>();
        userRole=new Role("ROLE_USER");
        userRole.setId(1);
        adminRole=new Role("ROLE_ADMIN");
        adminRole.setId(2);
        roles.add(userRole);
        roles.add(adminRole);


    }

  //for now we initialize the fields inside the method and not in the constructor, beacause more methods may be added that need different fields to initialize, we will see

    public User createUserwithRoleUser(){

        user =new User();
        user.setUsername("Nene");
        user.setDate(LocalDate.of( 2018 , Month.JANUARY , 23));
        user.setEmail("nene@example.com");
        user.setPassword("1234");
        user.addRole(userRole);
        user.setId(2);

        return user;

    }


    public User createUserWithBothRoles(){
        user =new User();
        user.setUsername("Katerina");
        user.setDate(LocalDate.of( 2018 , Month.JANUARY , 23));
        user.setEmail("katerina@example.com");
        user.setPassword("1234");
        user.addRole(userRole);
        user.addRole(adminRole);
        user.setId(1);
        return user;
    }


    public User createUserWithRoleAdmin(){

        user =new User();
        user.setUsername("Admin");
        user.setDate(LocalDate.of( 2018 , Month.JANUARY , 23));
        user.setEmail("admin@example.com");
        user.setPassword("1234");
        user.addRole(adminRole);
        user.setId(2);

        return user;
    }


}
