package com.springboot.rest.webservices.restfulwebservices.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.rest.webservices.restfulwebservices.user.User;

//Repository to talk to the database
public interface UserRepository extends JpaRepository<User,Integer>{

}