package com.bookstore.serviceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.domain.User;
import com.bookstore.domain.UserPayment;
import com.bookstore.repository.PaymentRepository;
import com.bookstore.service.PaymentService;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService{

	private static final Logger LOG=LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private PaymentRepository paymentRepo;
	
	@Override
	public UserPayment findById(Long id) {
		return paymentRepo.findUserPaymentById(id) ;
	}

	@Override
	public void removePaymentById(Long id) {
		paymentRepo.removeById(id);
	}

	@Override
	public void setDefaultCard(Long id, User user) {
		List<UserPayment> listUserPayment = paymentRepo.findAll();
		for(UserPayment userPayment:listUserPayment) {
			if(userPayment.getUser().getId() == user.getId()) {
				if(userPayment.getId() == id) {
					userPayment.setDefaultPayment(true);
					LOG.info("default set for-"+id);
				}
				else {
					LOG.info("default not set for-"+id);
					userPayment.setDefaultPayment(false);
				}
				paymentRepo.save(userPayment);
			}
		}
		
	}

	
	
}