package com.tts.techtalenttwitter.model;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Not a database object - a display object that takes tweet 
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TweetDisplay {
	private User user;
	private String message;
	private String date;
	private Date dateObjectDate;
	private List<Tag> tags;
	
	
	 
}
