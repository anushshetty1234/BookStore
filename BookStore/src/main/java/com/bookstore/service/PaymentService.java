package com.bookstore.service;

import com.bookstore.domain.User;
import com.bookstore.domain.UserPayment;

public interface PaymentService {

	UserPayment findById(Long id);
	void removePaymentById(Long id);
	void setDefaultCard(Long id,User user);
	
	
	
}
