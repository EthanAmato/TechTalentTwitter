package com.tts.techtalenttwitter.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tts.techtalenttwitter.model.Tweet;
import com.tts.techtalenttwitter.model.TweetDisplay;
import com.tts.techtalenttwitter.model.User;
import com.tts.techtalenttwitter.service.TweetService;
import com.tts.techtalenttwitter.service.UserService;

@Controller
public class TweetController {
	
	@Autowired
	private TweetService tweetService;
	@Autowired
	private UserService userService;
	
	
//	@GetMapping(path = {"/tweets", "/"})
//	public String getFeed(Model model) {
//		
//		List<TweetDisplay> tweets = new ArrayList<>();
//		
//		List<User> followedUsers = userService.getLoggedInUser().getFollowing();
//		
//		for(User user: followedUsers) {
//			tweets.addAll(tweetService.findAllByUser(user));
//		} //Gets the tweets from only followed users - don't need to loop through whole database of tweets
//		
//		tweets.addAll(tweetService.findAllByUser(userService.getLoggedInUser())); //add users tweets
//		
//		Collections.sort(tweets, (tweet1,tweet2) -> {
//			return tweet2.getDateObjectDate().compareTo(tweet1.getDateObjectDate());
//		}); // Arrow function that sorts based on created date so that feed is always showing the newest content
//		model.addAttribute("tweetList", tweets);
//
//		return "feed";
//	}
	
	//Alternative to my solution
	
	@GetMapping(path = {"/tweets", "/"})
	public String getFeed(@RequestParam(value="filter", required=false) String filter, Model model) {
		
		List<TweetDisplay> tweets = new ArrayList<>();
		User loggedInUser = userService.getLoggedInUser();
		
		if(filter ==null) {
			filter = "all";
		}
		if (filter.equalsIgnoreCase("following")) {
			List<User> following = loggedInUser.getFollowing(); 
			tweets = tweetService.FindAllByUsers(following);
			model.addAttribute("filter","following");
		} else {
			tweets = tweetService.findAll();
			model.addAttribute("filter","all");
		}
		model.addAttribute("tweetList", tweets);
		
		
		return "feed";
	}
	

	@GetMapping(path = "/tweets/{tag}")
	public String getTweetsByTag(@PathVariable(value = "tag") String tag,
									Model model) {
		List<TweetDisplay> tweets = tweetService.FindAllWithTag(tag);
		model.addAttribute("tweetList", tweets);
		model.addAttribute("tag", tag);
		
		return "taggedTweets";
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
