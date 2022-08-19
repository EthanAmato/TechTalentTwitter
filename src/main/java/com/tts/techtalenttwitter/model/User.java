package com.tts.techtalenttwitter.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor 
@Entity
@Table(name="user_profile") //lets you rename table (User is reserved word in h2)
public class User {
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="user_id") 
	private Long id;
	
	@Email(message = "Please provide a valid email")
	@NotEmpty(message = "Please Provide an Email")
	private String email;
	
	@Length(min=3, message = "Your username must have at least 3 characters")
	@Length(max=15, message = "Your username must have less than 15 characters")
	@Pattern(regexp="[^\\s]+", message="Your username cannot contain spaces")
	private String username;
	
	@Length(min=5, message="Your password must have at least 5 characters")
	private String password;
	
	@NotEmpty(message = "Please provide your first name")
	private String firstName;
	
	@NotEmpty(message = "Please provide your last name")
	private String lastName;
	private int active;
	
	@CreationTimestamp
	private Date createdAt;
	
	//Lombok lets us avoid creating a bunch of getters + setters, toString(), equals, requiredARgsConstructor, hashcode
	
	
	//Associates roles with user - how does it map to the database?
	//We generally do not store multiple elements in a single field in a database - many to many association in separate table
	
	@ManyToMany(cascade = CascadeType.ALL) //can you delete a role if there's no user using it? Basically means that if we delete a role, all users having that role will lose that role,
										  //similarly
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	
	private Set<Role> roles;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_follower", joinColumns = @JoinColumn(name = "user_id"),
				inverseJoinColumns = @JoinColumn(name="follower_id")) //still a pointer to a user's id but just has name follower_id
	private List<User> followers;
	
	@ManyToMany(mappedBy="followers") //relationship already defined by user_follower so basically looking at table from two diff sides
	private List<User> following;
}
