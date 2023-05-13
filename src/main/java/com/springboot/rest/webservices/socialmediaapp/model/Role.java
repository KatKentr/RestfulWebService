package com.springboot.rest.webservices.socialmediaapp.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="roles")
public class Role {

    @Id
    @GeneratedValue
    private Integer id;

    private String roleType;


    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<User> users;

    public Set<User> getUsers() {
        return users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole_type() {
        return roleType;
    }

    public void setRole_type(String roleType) {
        this.roleType = roleType;
    }


}
