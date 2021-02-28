package com.bookstore.service;

import org.springframework.stereotype.Service;

import com.bookstore.domain.UserBilling;

@Service
public interface BillingService {

	UserBilling findUserBillingById(Long id);
	
}
