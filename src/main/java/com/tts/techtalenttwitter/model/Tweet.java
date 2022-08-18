package com.tts.techtalenttwitter.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Getters + Setters + toSTring + hash
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tweet {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "tweet_id")
	private Long id;
	
	//USER is a complex object that can't be stored in a single database field
	//What we want to do is store a foreign key to the user associated with each tweet instead
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false) //knows that user is to be stored as a foreign key
	@JoinColumn(name="user_id")
	@OnDelete(action = OnDeleteAction.CASCADE) //If user is deleted, all tweets associated with user are also deleted
	private User user;
	
	@NotEmpty(message = "Tweet Cannot be Empty")
	@Length(max = 280, message = "Tweet cannot have more than 280 characters")
	private String message;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "tweet_tag", joinColumns = @JoinColumn(name = "tweet_id"),
				inverseJoinColumns = @JoinColumn(name="tag_id"))
	private List<Tag> tags;
	
	
	@CreationTimestamp
	private Date createdAt;
}
