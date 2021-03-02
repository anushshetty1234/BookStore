package com.bookstore.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.domain.Order;
import com.bookstore.domain.ShoppingCart;
import com.bookstore.domain.User;
import com.bookstore.domain.UserBilling;
import com.bookstore.domain.UserPayment;
import com.bookstore.domain.UserShipping;
import com.bookstore.serviceImpl.EmailService;
import com.bookstore.serviceImpl.OrderServiceImpl;
import com.bookstore.serviceImpl.ShopingCartServiceImpl;
import com.bookstore.serviceImpl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/order")
public class CheckoutController {

	private static final Logger log = LoggerFactory.getLogger(CheckoutController.class);
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Autowired
	private OrderServiceImpl orderServiceImpl;
	
	@Autowired
	ShopingCartServiceImpl shoppingCartServiceImpl;
	
	@Autowired
	EmailService emailSearvice;
	
	@RequestMapping(value = "/checkout",method = RequestMethod.POST)
	public Order checkout(@RequestBody HashMap<String, Object> map,Principal principal) throws MessagingException {
		Order order = null;
		if (principal != null) {
			String username = principal.getName();

			if (username != null) {
				User user = userServiceImpl.findUserByUsername(principal.getName());

				ObjectMapper mapper = new ObjectMapper();
				if (map != null) {
					try {
						String shippingMethod = mapper.convertValue(map.get("shippingMethod"),String.class);
						UserShipping shippingAddress = mapper.convertValue(map.get("userShipping"), UserShipping.class);
						UserBilling userBilling = mapper.convertValue(map.get("userBilling"), UserBilling.class);
						UserPayment payment = mapper.convertValue(map.get("userPayment"),UserPayment.class);
						ShoppingCart cart = shoppingCartServiceImpl.loadShoppngCartSerice(user);
						
						order = orderServiceImpl.createOrder(shippingMethod,shippingAddress,userBilling,payment,cart,user);
						
						String emailSent=emailSearvice.sendOrderConfirmation(order,user,shippingAddress,order.getCartItemList());
						
						log.info("Order confirmation Email status-"+emailSent);

					
					} catch (Exception e) {
						log.error("failed to create order "+e.getMessage()+e);
					}
				}

			}
		}
		return order;
	}
	
	@RequestMapping(value = "/getOrderList",method = RequestMethod.GET)
	public List<Order> getOrderList(Principal principal){
		try {
			if (principal != null) {
				String username = principal.getName();
				User user = userServiceImpl.findUserByUsername(username);
				return user.getOrderList();
			}
		} catch (Exception e) {
			log.error("failed to get orderList " + e.getMessage() + e);
		}
		return null;
		
	}
}
