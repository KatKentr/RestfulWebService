package com.springboot.rest.webservices.socialmediaapp.security;

import static org.springframework.security.config.Customizer.withDefaults;

import com.springboot.rest.webservices.socialmediaapp.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.springboot.rest.webservices.socialmediaapp.constants.ApiRoutes;


//We ovwrwrite the default Spring SecurityFilterChain
//@Configuration
//@EnableMethodSecurity
//@EnableWebSecurity
public class SecurityConfig {
	
	private CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }
    
    
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
                                 AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    
    
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		//All requests should be authenticated
		http.authorizeHttpRequests(
												
				auth ->auth
				.requestMatchers(ApiRoutes.Auth.REGISTER).permitAll()
				.requestMatchers(ApiRoutes.Auth.LOGIN).permitAll()
						//.requestMatchers("/app/v1/users").hasAuthority("user_role")
						.requestMatchers("/app/v1/users/*").hasAuthority("user_role")
						.requestMatchers("/app/v1/users").permitAll()
						.anyRequest().authenticated()
				);

	//We want to enable basic authentication
		http.httpBasic(withDefaults());
		
	//Disable CSRF to enable POST, PUT requests
		http.csrf().disable();
		
		return http.build();
		
		
	}

}
