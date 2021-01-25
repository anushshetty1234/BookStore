package com.bookstore.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.domain.User;
import com.bookstore.domain.UserBilling;
import com.bookstore.domain.UserPayment;
import com.bookstore.service.UserService;
import com.bookstore.serviceImpl.PaymentServiceImpl;
import com.bookstore.serviceImpl.UserServiceImpl;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Autowired
	PaymentServiceImpl paymentServiceImpl;
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public ResponseEntity addNewCardDetail(@RequestBody UserPayment userPayment,Principal principal) {
		
		if(principal != null) {
			String username = principal.getName();
			User user = userServiceImpl.findUserByUsername(username);
			UserBilling userBilling = userPayment.getUserBilling();
			paymentServiceImpl.updateUserPayment(user,userBilling,userPayment);
			return new ResponseEntity("Your card has been successfully saved",HttpStatus.OK);
		}
		return new ResponseEntity("Card not saved",HttpStatus.BAD_REQUEST);
	}


	@RequestMapping(value = "/remove",method = RequestMethod.POST)
	public ResponseEntity removeCardDetail(@RequestBody String id,Principal principal) {
		
		if(principal != null && id != null) {
			
			paymentServiceImpl.removePaymentById(Long.parseLong(id));
			
			return new ResponseEntity("Your card has been successfully removed",HttpStatus.OK);
		}
		return new ResponseEntity("Card not removed",HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/setDefault",method = RequestMethod.POST)
	public ResponseEntity setDefaultCard(@RequestBody String id,Principal principal) {
		if(principal != null && id != null) {
			String username = principal.getName();
			User user = userServiceImpl.findUserByUsername(username);
			paymentServiceImpl.setDefaultCard(Long.parseLong(id),user);
			
			return new ResponseEntity("Card set as default",HttpStatus.OK);
		}
		return new ResponseEntity("Request failed",HttpStatus.BAD_REQUEST);
	}

	
	@RequestMapping(value = "/getPaymentList")
	public List<UserPayment> getPaymentList(Principal principal) {
		if(principal != null) {
			String username = principal.getName();
			User user = userServiceImpl.findUserByUsername(username);
			return user.getUserPaymentList();
		}
		return null;
	}



}
