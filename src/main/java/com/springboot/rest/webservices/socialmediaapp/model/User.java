package com.springboot.rest.webservices.socialmediaapp.model;

import java.time.LocalDate;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor


//@Builder
@Entity(name="user_details")     //User is a keyword in postgres and h2. Errors encountered, hence table name should be changed
public class User implements UserDetails {
	
    @Id
    @GeneratedValue
	private Integer id;
	

	@Column(name="name")
	//private String name;
	private String username;
	
	private LocalDate date;
	
	private String email;
	
	@JsonIgnore  //when retrieving user data, do not include password in the json response
	private String password;
	
	
	//A user will have a list of posts
	@OneToMany(mappedBy="user",cascade = CascadeType.ALL,orphanRemoval = true)   //the field in the Post class that owns this relationship. CascadeType.All and orphanRemoval true: Child entities (post) of a user (parent entity) will be deleted, when the user is deleted
	@JsonIgnore                //we don't want post to be part of the json reponses for the user bean
	private List<Post> posts;
	
	//A user may have multiple comments
	@OneToMany(mappedBy="user",cascade = CascadeType.ALL,orphanRemoval = true)   //the field in the Comment class that owns this relationship. CascadeType.All and orphanRemoval true: Child entities (comment) of a user (parent entity) will be deleted, when the user is deleted
	@JsonIgnore                //we don't want post to be part of the json reponses for the user bean
	private List<Comment> comments;


	@ManyToMany()   //cascade = CascadeType.ALL it is not correct though. Adding a new user with a role attribute, adds a new role entry in the roles table and Removing the user, deletes the role entry in the roles table (the second parent entity). The roles table should have predefined entries(specifc roles) that remain unaffected when new users are added or deleted
	//@JsonIgnore
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Set<Role> roles=new HashSet<>();
	
	


	//users can follow other users
	@ManyToMany()
	@JoinTable(name="users_follows",joinColumns = @JoinColumn(name="user_id",referencedColumnName = "id"),inverseJoinColumns=@JoinColumn(name="follows_user_id",referencedColumnName = "id"))
	@JsonIgnore
	private Set<User>  following=new HashSet<>();

    @ManyToMany(mappedBy = "following")
	private Set<User>  followers=new HashSet<>();

	public void addFollowing(User newUserToFollow){  //add a new user that would like to follow


		this.following.add(newUserToFollow);
		newUserToFollow.followers.add(this);
	}

	public void removeFollowing(User userToUnfollow){

		this.following.remove(userToUnfollow);
		userToUnfollow.followers.remove(this);
	}


	public User() {   //we need a default constructor when we make use of jpa

	}

	
	public User(Integer id, String name, LocalDate date, String email,String password) {
		super();
		this.id = id;
		this.username = name;
		this.date = date;
		this.email=email;
		this.password=password;

	}


	public int getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}

	
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
	public List<Post> getPosts() {
		return posts;
	}
	
	//comments
	
	public List<Comment> getComments() {
		return comments;
	}


	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	
	//email, password
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	public Set<User> getFollowing() {
		return following;
	}

	public void setFollowing(Set<User> following) {
		this.following = following;
	}

	public Set<User> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<User> followers) {
		this.followers = followers;
	}

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authorities;
	}

	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getUsername() {
		return username;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return true;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void addRole(Role role) {
		this.roles.add(role);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + username + ", date=" + date + "]";
	}



	
	
	

}
