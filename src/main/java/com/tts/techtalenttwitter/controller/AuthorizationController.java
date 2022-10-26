package com.tts.techtalenttwitter.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.tts.techtalenttwitter.model.User;
import com.tts.techtalenttwitter.service.UserService;

//This class will listen to the login page and display login form
//It also will handle our signup form


@Controller
public class AuthorizationController {
	
	@Autowired
	UserService userService;
	
	
	@GetMapping(path = "/login")
	public String login() {
		return "login.html";
	}
	
	@GetMapping(path = "/logout")
	public String logout() {
		return "login";
	}
	
	@GetMapping(path = "/signup")
	public String registration(Model model) {
		
		User user = new User();
		model.addAttribute("user",user);
		
		return "registration";
	}
	
	@PostMapping(path = "/signup")
	public String createNewUser(@Valid User user, BindingResult bindingResult, Model model) { //valid checks user as it comes in
		
		User userExist = userService.findByUsername(user.getUsername());
		if(userExist != null) {
			bindingResult.rejectValue("username", "error.user","Username already taken"); //automatically passed to thymeleaf			
			//User already exists - handle error
		} else if (!bindingResult.hasErrors()){ //saves if it doesn't have errors
			System.out.println(user);
			userService.saveNewUser(user);
			model.addAttribute("success","Sign Up Successful!");
			model.addAttribute("user", new User()); //returns blank user and returns blank registration form again
		}
		return "registration";
	}
	
	
}
