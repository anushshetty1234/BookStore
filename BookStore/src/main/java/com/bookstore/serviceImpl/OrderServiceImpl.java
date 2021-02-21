package com.bookstore.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.controller.CheckoutController;
import com.bookstore.domain.CartItem;
import com.bookstore.domain.Order;
import com.bookstore.domain.ShoppingCart;
import com.bookstore.domain.User;
import com.bookstore.domain.UserBilling;
import com.bookstore.domain.UserPayment;
import com.bookstore.domain.UserShipping;
import com.bookstore.repository.BillingRepository;
import com.bookstore.repository.OrderRepository;
import com.bookstore.repository.PaymentRepository;
import com.bookstore.repository.ShippingRepository;
import com.bookstore.service.OrderService;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	OrderRepository orderRepo;
	
	@Autowired
	BillingRepository billingRepo;
	
	@Autowired
	ShippingRepository shippingRepo;
	
	@Autowired
	PaymentRepository paymentRepo;
	
	@Autowired
	ShopingCartServiceImpl cartServiceImpl;
	
	public Order createOrder(String shippingMethod, UserShipping shippingAddress, UserBilling userBilling,
			UserPayment payment, ShoppingCart cart, User user) {
		
		Order order = null;
		try {
		
			userBilling = billingRepo.save(userBilling);
			shippingAddress = shippingRepo.save(shippingAddress);
			payment = paymentRepo.save(payment);
					
			order = new Order();

			LocalDateTime timestamp = LocalDateTime.now();

			order.setBillingAddress(userBilling);
			order.setPayment(payment);
			order.setShippingAddress(shippingAddress);
			order.setShippingMethod(shippingMethod);
			order.setCartItemList(cart.getCartItemList());
	
			for(CartItem eachItem:cart.getCartItemList()) {
				eachItem.setOrder(order);
			}
			cartServiceImpl.clearShoppingCart(cart);
			
			order.setOrderTotal(cart.getGrandTotal());
			order.setOrderDate(timestamp);
			order.setUser(user);

			LocalDate today = LocalDate.now();
			
			if (shippingMethod.equalsIgnoreCase("groundShipping")) {
				order.setShippingDate(today.plusDays(5));
			} else {
				order.setShippingDate(today.plusDays(3));
			}
			order.setOrderStatus("placed");
			
			order = orderRepo.save(order);
			
		} catch (Exception e) {
			log.error("Error occured when persisting order " + e + e.getMessage());
		}
		return order;
	}

	
	
}
