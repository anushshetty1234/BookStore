package com.bookstore.service;

import com.bookstore.domain.Order;
import com.bookstore.domain.ShoppingCart;
import com.bookstore.domain.User;
import com.bookstore.domain.UserBilling;
import com.bookstore.domain.UserPayment;
import com.bookstore.domain.UserShipping;

public interface OrderService {

	public Order createOrder(String shippingMethod, UserShipping shippingAddress, UserBilling userBilling,
			UserPayment payment, ShoppingCart cart, User user);
	
}
