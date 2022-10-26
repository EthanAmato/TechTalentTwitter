package com.tts.techtalenttwitter.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	@Autowired
	private DataSource dataSource; //telling springboot to use default datasource
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	//We are configuring SpringSecurity, WebSecurityConfigurerAdapter is a config class
	//provided by SpringSecurity - by default it sets Spring Security up in a locked down configuration by
	//inheriting it and overriding certain methods, we can config Spring Security to be configured differently
	
	@Value("${spring.queries.users-query}") //imports variable from our application.properties (select username, password, active from user_profile where username=?)
	private String usersQuery;
	
	@Value("${spring.queries.roles-query}")  //imports variable from our application.properties (select u.username, r.role from user_profile u inner join user_role ur on(u.user_id=ur.user_id) inner 
											 //join role r on(ur.role_id=r.role_id) where u.username=?)
	private String rolesQuery;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.jdbcAuthentication()
			.usersByUsernameQuery(usersQuery)
			.authoritiesByUsernameQuery(rolesQuery)
			.dataSource(dataSource)
			.passwordEncoder(bCryptPasswordEncoder);
		 // basically says we want to use database-based authentication 
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests() //configures what PAGES can be accessed
				.antMatchers("/login").permitAll()
				.antMatchers("/signup").permitAll()
				.antMatchers("/custom.js").permitAll()
				.antMatchers("/custom.css").permitAll() //All other pages will not be accessible by default 
				.antMatchers().hasAuthority("USER").anyRequest().authenticated() //UNLESS YOU HAVE AUTHORITY, in that case your every request is authenticated/allowed
			.and()//back to configuring http
				.formLogin() //tells spring security that we are going to login using a webpage that has a form
					.loginPage("/login")
					.failureUrl("/login?error=true") // if it fails go here
					.defaultSuccessUrl("/tweets") //if succeeds go to tweets page
					.usernameParameter("username") //takes username + password as parameters for login
					.passwordParameter("password")
			.and()
				.logout() //what triggers a logout? we go to /logout page
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.and()
				.exceptionHandling()
			.and()
				.csrf().disable()
				.headers().frameOptions().disable(); 
					
	}

	@Override 
	public void configure(WebSecurity web) throws Exception { //configures what files can be accessed
		web.ignoring()
			.antMatchers("/resources/**","/static/**","/css/**","/js/**","/images/**");
		
	}



}

