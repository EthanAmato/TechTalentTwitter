package com.tts.techtalenttwitter.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.tts.techtalenttwitter.model.Tweet;
import com.tts.techtalenttwitter.model.User;
import com.tts.techtalenttwitter.service.TweetService;
import com.tts.techtalenttwitter.service.UserService;

@Controller
public class TweetController {
	
	@Autowired
	private TweetService tweetService;
	@Autowired
	private UserService userService;
	
	
	@GetMapping(path = {"/tweets", "/"})
	public String getFeed(Model model) {
		
		List<Tweet> tweets = tweetService.findAll();
		
		model.addAttribute("tweetList", tweets);
		
		
		return "feed";
	}
	
	@GetMapping(path="/tweets/new")
	public String getTweetForm(Model model) {
		model.addAttribute("tweet",new Tweet());
		
		return "newTweet";
	}
	
	@PostMapping(path = "/tweets")
	public String submitTweetForm(@Valid Tweet tweet, BindingResult bindingResult, Model model) {
		//tweet comes back from form
		User user = userService.getLoggedInUser();
		if (!bindingResult.hasErrors()) {
			tweet.setUser(user);
			tweetService.save(tweet);
			
			model.addAttribute("successMessage","Tweet successfully created!");
			model.addAttribute("tweet",new Tweet());
			
		} 
		
		return "newTweet";
		
	}
	
	
	
}
