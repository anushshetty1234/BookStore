package com.bookstore.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.config.SecurityUtility;
import com.bookstore.domain.ShoppingCart;
import com.bookstore.domain.User;
import com.bookstore.domain.UserPayment;
import com.bookstore.domain.UserShipping;
import com.bookstore.domain.security.Role;
import com.bookstore.domain.security.UserRole;
import com.bookstore.service.UserService;
import com.bookstore.serviceImpl.UserSecurityService;
import com.bookstore.utility.MailConstructor;


@RestController
@RequestMapping("/user")
public class UserController {
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MailConstructor mailConstructor;
	
	@Autowired
	private JavaMailSender mailSender;
	
	
	@Transactional
	@RequestMapping("/newUser")
	public ResponseEntity createNewUserForStoreFront(HttpServletRequest req,@RequestBody HashMap<String, String> mapper) {
				
		String userName = mapper.get("username");
		String email = mapper.get("email");
		
		if(userService.findUserByUsername(userName) != null) {
			return new ResponseEntity("usernameExists",HttpStatus.BAD_REQUEST);
		}
		
		if(userService.findUserByEmail(email) != null) {
			return new ResponseEntity("emailExists",HttpStatus.BAD_REQUEST);
		}
		
		User user = new User();
		user.setUserName(userName);
		user.setEmail(email);
		
		String password=SecurityUtility.randomPassword();	
		user.setPassword(SecurityUtility.passwordEncoder().encode(password));
		
		Role role = new Role();
		role.setRoleId((long)1);
		role.setRoleName("ROLE_USER");
		
		UserRole userRole = new UserRole(user, role);
		Set<UserRole> userRoles=new HashSet<UserRole>();
		
		userRoles.add(userRole);
		
		userService.CreateUser(user, userRoles);
		
		SimpleMailMessage sendMail=mailConstructor.constructMailMessage(user, password);
		mailSender.send(sendMail);

		return new ResponseEntity("user Created",HttpStatus.OK);
	}
	
	
	@Transactional
	@RequestMapping(value = "/forgotPassword",method = RequestMethod.POST)
	public ResponseEntity forgetPassword(HttpServletRequest req,@RequestBody HashMap<String, String> mapper) {
		
		log.info(mapper.toString());
		
		User user = userService.findUserByEmail(mapper.get("email"));
		
		if(user == null) {
			return new ResponseEntity("Email not found",HttpStatus.BAD_REQUEST);
		}
		
		
		String password=SecurityUtility.randomPassword();	
		user.setPassword(SecurityUtility.passwordEncoder().encode(password));
		userService.save(user);
		
		SimpleMailMessage sendNewMail=mailConstructor.constructMailMessage(user, password);
		mailSender.send(sendNewMail);

		
		return new ResponseEntity("Email sent",HttpStatus.OK);
	}
	
	
	
	
	@Transactional
	@RequestMapping(value ="/updateUserInfo",method = RequestMethod.POST)
	public ResponseEntity updateUserInfo(HttpServletRequest req,@RequestBody HashMap<String, String> mapper) throws Exception {
		
		Long id = Long.parseLong(mapper.get("id"));
		String userName = mapper.get("username");
		String email = mapper.get("email");
		String firstName = mapper.get("fristname");
		String lastName = mapper.get("lastname");
		String currentPassword = mapper.get("currentPassword");

		String newPassword = mapper.get("password");
		
		User currentUser = userService.findById(id);
		
		if(currentUser == null) {
			throw new Exception("User not found");
		}
				
		if(userService.findUserByUsername(userName) != null) {
			if(userService.findUserByUsername(userName).getId() != currentUser.getId()) {
				return new ResponseEntity("Username already taken",HttpStatus.BAD_REQUEST);
			}
		}
		
		if(userService.findUserByEmail(email) != null) {
			if(userService.findUserByEmail(email).getId() != currentUser.getId()) {
				return new ResponseEntity("Email already taken",HttpStatus.BAD_REQUEST);
			}
		}
		
			BCryptPasswordEncoder encoder = SecurityUtility.passwordEncoder();
			
			if(currentPassword != null) {
			if(encoder.matches(currentPassword,currentUser.getPassword())) {
				if(newPassword !=null && !newPassword.isEmpty() && newPassword != "") {
						currentUser.setPassword(encoder.encode(newPassword));
				}
				currentUser.setUserName(userName);
				currentUser.setEmail(email);
				
			}
			else {
				return new ResponseEntity("Incorrect current password",HttpStatus.BAD_REQUEST);
			}
		
			}

		currentUser.setFirstName(firstName);
		currentUser.setLastName(lastName);
		
		userService.save(currentUser);
		
		return new ResponseEntity("Update Success",HttpStatus.OK);
	}
	
	@RequestMapping("/getCurrentUser")
	public User getCurrentUser(Principal principal) {
		User user = null;
		String username = null;
		if(principal != null)
		{
			username = principal.getName();
		}
		if(username != null) {
			user = userService.findUserByUsername(principal.getName());
		}
		return user;
	}
	
	

}
