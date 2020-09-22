package com.bookstore.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bookstore.domain.User;
import com.bookstore.repository.UserRepository;


@Service
public class UserSecurityService implements UserDetailsService{
	
	private static final Logger Log=LoggerFactory.getLogger(UserSecurityService.class);
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		 User user=userRepository.findByUserName(username);
			
		if(user==null) {
			Log.warn("No user with username {} found", username);
			throw new UsernameNotFoundException("Username "+username+" not found");
		}
		
		return user;
	}
	
	
	
}
