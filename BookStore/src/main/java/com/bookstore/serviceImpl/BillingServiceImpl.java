package com.bookstore.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.domain.UserBilling;
import com.bookstore.repository.BillingRepository;
import com.bookstore.service.BillingService;

@Service
public class BillingServiceImpl implements BillingService {

	@Autowired
	BillingRepository billingRepo;

	@Override
	public UserBilling findUserBillingById(Long id) {
		return billingRepo.findUserBillingById(id);
	}

	
	
}
