package com.tts.techtalenttwitter.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tts.techtalenttwitter.model.User;

@Repository
public interface UserRepository extends CrudRepository<User,Long>{
	User findByUsername(String username); //parsed by jpa to find user by inputted string
	
	@Override
	List<User> findAll(); //makes sure findAll sends us back list instead of iterable
}
