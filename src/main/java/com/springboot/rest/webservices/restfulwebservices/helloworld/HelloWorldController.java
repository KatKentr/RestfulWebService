package com.springboot.rest.webservices.restfulwebservices.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

//Typically REST APIs return JSON back and not Strings
@RestController
public class HelloWorldController {
	
	
	@GetMapping(path="/hello-world") //we return a String back
	public String helloWorld() {
		
		return "Hello World";
	}
	
	
	@GetMapping(path="/hello-world-bean")
	public HelloWorldBean helloWorldBean() {  //we return an instance of the class HelloWorldBean, it is automatically converted into a JSON response
		
		return new HelloWorldBean("Hello World");
	}
	
	//Path Parameters
    // /users/{id}/todos/{todo_id} . E.g : /user/2/todos/200/  The variable values (2,200) are called path parameters. This could be the URL to access a specific todo of a specific user
	// /hello-world/path-variable/{name}
	//  /hello-world/path-variable/{name} . We will capture the value held in this variable(name).
	@GetMapping(path = "/hello/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldBeanPathVariable(@PathVariable String name) { 
		return new HelloWorldBean(String.format("Hello World, %s",name));
		
	}
	
	

}
