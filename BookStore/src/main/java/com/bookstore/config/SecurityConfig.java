package com.bookstore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import com.bookstore.serviceImpl.UserSecurityService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	Environment env; 

	@Autowired
	UserSecurityService useSecurityService;
	
	private BCryptPasswordEncoder passwordEncoder() {
		return SecurityUtility.passwordEncoder();
	}
	
	private static final String[] PUBLIC_MATHCES= {
			"/css/**",
			"/js/**",
			"/images/**",
			"/book/**",
			"/user/**",
			"/media/**"
	};

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(useSecurityService).passwordEncoder(passwordEncoder());
		
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(PUBLIC_MATHCES).permitAll()
			.antMatchers("/admin/token").hasAuthority("ROLE_ADMIN")	
			.anyRequest().authenticated();

		http.csrf().disable()
		    .cors()
		    .and()
			.httpBasic();

		
	}
	 
	  @Bean
	  public HttpSessionIdResolver httpSessionStrategy() {
		  return  HeaderHttpSessionIdResolver.xAuthToken();
	  }
	  
	 	
	
}
