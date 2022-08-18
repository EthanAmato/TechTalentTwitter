package com.tts.techtalenttwitter.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tts.techtalenttwitter.model.Tag;
import com.tts.techtalenttwitter.model.Tweet;
import com.tts.techtalenttwitter.model.User;
import com.tts.techtalenttwitter.repository.TagRepository;
import com.tts.techtalenttwitter.repository.TweetRepository;

@Service
public class TweetService {
	
	@Autowired
	private TweetRepository tweetRepository;
	
	@Autowired
	private TagRepository tagRepository;
	
	public List<Tweet> findAll() {
		List<Tweet> tweets =tweetRepository.findAllByOrderByCreatedAtDesc();
		return formatTweets(tweets);
	}
	
	public List<Tweet> findAllByUser(User user){
		List<Tweet> tweets = tweetRepository.findAllByUserOrderByCreatedAtDesc(user);
		return formatTweets(tweets);
	}
	
	public List<Tweet> FindAllByUsers(List<User> users){
		List<Tweet> tweets = tweetRepository.findAllByUserInOrderByCreatedAtDesc(users);
		return formatTweets(tweets);
	}
	
	public List<Tweet> FindAllWithTag(String tag){
	    List<Tweet> tweets = tweetRepository.findByTags_PhraseOrderByCreatedAtDesc(tag);
		return formatTweets(tweets);
	}
	
	

	public void save(Tweet tweet) {
		handleTags(tweet);
		tweetRepository.save(tweet);
	}
	
	private void handleTags(Tweet tweet) {
		List<Tag> tags = new ArrayList<Tag>(); //store the tags we find in here
		Pattern pattern = Pattern.compile("#\\w+"); //catches words where 
		Matcher matcher = pattern.matcher(tweet.getMessage()); //finds list of all matches to pattern
		
		while(matcher.find()) {
			String phrase = matcher.group();
			phrase = phrase.substring(1).toLowerCase(); // gets rid of # sign and lowercases the tag
			
			Tag tag = tagRepository.findByPhrase(phrase);
			
			if(tag == null) { //if not found in repo we add it
				tag = new Tag();
				tag.setPhrase(phrase);
				tag = tagRepository.save(tag);
			}
			tags.add(tag);
		}
		
		tweet.setTags(tags); //updates tweet with tag information
		
	}
	
	private List<Tweet> formatTweets(List<Tweet> tweets) {
		addTagLinks(tweets);
		return tweets;
	}

	private void addTagLinks(List<Tweet> tweets) {

		Pattern pattern = Pattern.compile("#\\w+");
		for (Tweet tweet: tweets) {
			String message = tweet.getMessage();
			Matcher matcher = pattern.matcher(message);
			
			Set<String> tags = new HashSet<String>();
			if(matcher.find()) {
				tags.add(matcher.group()); //identifies all matches
			}
			for(String tag: tags) {
				String link = "<a class=\"tag\" href=\"/tweets/";
				link+= tag.substring(1).toLowerCase();
				link+= "\">" + tag + "</a>";
				message = message.replaceAll(tag, link);
			}
			tweet.setMessage(message);
		}
		
	}

}
