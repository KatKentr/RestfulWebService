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

    private String name;

    public Role() { }

    public Role(String name) {
        this.name = name;
    }

    public Role(Integer id) {
        super();
        this.id = id;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }


}
