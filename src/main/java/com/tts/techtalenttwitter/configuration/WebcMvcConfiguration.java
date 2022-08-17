package com.tts.techtalenttwitter.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//This class is meant to 

@Configuration //this annotation basically says that this class is a class that holds configuration info
			   //One of the things you can put into a config class is definitions on how to create objects
			   //that are autowired

public class WebcMvcConfiguration implements WebMvcConfigurer{ 
	@Bean
	public BCryptPasswordEncoder passwordEncoder() { //whenever SpringBoot needs a BCryptPasswordEncoder it will run this code
		BCryptPasswordEncoder bCrpytPasswordEncoder = new BCryptPasswordEncoder();
		return bCrpytPasswordEncoder;
	}
	
	
	
}
