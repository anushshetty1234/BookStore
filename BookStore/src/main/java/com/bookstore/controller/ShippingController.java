package com.bookstore.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.domain.User;
import com.bookstore.domain.UserBilling;
import com.bookstore.domain.UserShipping;
import com.bookstore.serviceImpl.ShippingServiceImpl;
import com.bookstore.serviceImpl.UserServiceImpl;

@RestController
@RequestMapping(value = "/shipping")
public class ShippingController {

	private static final Logger log = LoggerFactory.getLogger(ShippingController.class);
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Autowired
	ShippingServiceImpl shippingServiceImpl;
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public ResponseEntity addNewShipping(@RequestBody UserShipping userShipping,Principal principal) {
		
		if(principal != null) {
			try {
				String username = principal.getName();
				User user = userServiceImpl.findUserByUsername(username);
				shippingServiceImpl.addNewShippingToUser(userShipping, user);
				return new ResponseEntity("Your shipping data has been saved successfully",HttpStatus.OK);
			}
			catch(Exception e) {
				log.error(e.getMessage());
			}
		}
	
		return new ResponseEntity("Failed to save shipping data!",HttpStatus.BAD_REQUEST);
	}
		
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public ResponseEntity removeShipping(@RequestBody String id,Principal principal) {
		
		if(principal != null) {
			try {
				shippingServiceImpl.removeShippingData(Long.parseLong(id));
				return new ResponseEntity("Your shipping data has been deleted successfully",HttpStatus.OK);
			}
			catch(Exception e) {
				log.error(e.getMessage());
			}
		}
	
		return new ResponseEntity("Failed to delete shipping data!",HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/setDefault",method = RequestMethod.POST)
	public ResponseEntity setDefaultShipping(@RequestBody String id,Principal principal) {
		
		if(principal != null) {
			try {
				String username = principal.getName();
				User user = userServiceImpl.findUserByUsername(username);
				shippingServiceImpl.setDefaultShippingData(Long.parseLong(id),user);
				return new ResponseEntity("Your default shipping  has been set successfully",HttpStatus.OK);
			}
			catch(Exception e) {
				log.error(e.getMessage());
			}
		}
	
		return new ResponseEntity("Failed to set default shipping!",HttpStatus.BAD_REQUEST);
	}
	
}
