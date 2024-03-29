package com.springboot.rest.webservices.socialmediaapp.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class AuthRequest {
    @NotNull
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotNull
    @NotEmpty(message = "Password cannot be empty")
    private String password;

    public AuthRequest() {

    }

    public AuthRequest(String email,String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
