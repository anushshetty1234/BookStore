package com.bookstore.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.bookstore.domain.User;

@Component
public class MailConstructor {

	@Autowired
	Environment env;
	
	public  SimpleMailMessage constructMailMessage(User user,String password) {
		String message = "\n Please use below credentials to login to new Bookstore account"
				+ "\n Username:"+user.getUsername()+"\nPassword:"+password;
		
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject("BookStore new account");
		email.setFrom(env.getProperty("support.email"));
		email.setText(message);
		return email;
	}
	
}
