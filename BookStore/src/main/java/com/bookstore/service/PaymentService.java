package com.bookstore.service;

import com.bookstore.domain.User;
import com.bookstore.domain.UserBilling;
import com.bookstore.domain.UserPayment;

public interface PaymentService {

	UserPayment findById(Long id);
	void removePaymentById(Long id);
	void setDefaultCard(Long id,User user);
	void updateExistingUserPaymentInfo(User user,UserBilling userBilling,UserPayment userPayment);
	void updateUserPayment(User user, UserBilling userBilling, UserPayment userPayment);
	
	
}
