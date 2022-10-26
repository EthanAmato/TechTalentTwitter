package com.tts.techtalenttwitter.configuration;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class PostgresConfiguration {
	
	
	
	//Create database ourselves
	@Bean
	public DataSource dataSource() throws URISyntaxException {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		URI dbUri = new URI(dotenv.get("DATABASE_URL")); //allows us to parse our db url
										  //and break it into diff parts
		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + 
						dbUri.getHost() + ":" + 
						dbUri.getPort() + 
						dbUri.getPath() + 
						"?sslmode=require"; //forces encryption
		
		
		
		DataSourceBuilder<? extends DataSource> dataSourceBuilder = DataSourceBuilder.create();
		return dataSourceBuilder.driverClassName("org.postgresql.Driver")
								 .url(dbUrl)
								 .username(username)
								 .password(password)
								 .build();
		
	}
}
