package com.tts.techtalenttwitter.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tts.techtalenttwitter.model.Role;
import com.tts.techtalenttwitter.model.User;
import com.tts.techtalenttwitter.repository.RoleRepository;
import com.tts.techtalenttwitter.repository.UserRepository;

@Service
public class UserService {
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	//Springboot will want to call this constructor itself and will do that using Autowired
	
	//as of Spring 4.3 this is no longer necessary but ill leave it in for now
	public UserService(UserRepository userRepository, 
					   RoleRepository roleRepository,
					   BCryptPasswordEncoder bCryptPasswordEncoder) { //SB can't make this by default - need config file to tell it how the encoder is made
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	public void save(User user) { //always a good idea to have a save in a service class
		userRepository.save(user);
	}
	
	public User saveNewUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); //encrypt password
		user.setActive(1); //set them as active
		Role userRole = roleRepository.findByRole("USER");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole))); //give them role of "USER"
		
		return userRepository.save(user);//also updates the roles table with the roles given to user
		
	}
	
	public User getLoggedInUser() {
		String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		return findByUsername(loggedInUsername);
	}
	
}
