package com.springboot.rest.webservices.restfulwebservices.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


//We ovwrwrite the default Spring SecurityFilterChain
@Configuration
public class SpringSecurityConfiguration {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		//All requests should be authenticated
		http.authorizeHttpRequests(
				
				auth ->auth.anyRequest().authenticated()
				
				);
	//We want to enable basic authentication
		http.httpBasic(withDefaults());
		
	//Disable CSRF to enable POST, PUT requests
		http.csrf().disable();
		
		
		
		return http.build();
		
		
	}

}
