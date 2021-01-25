package com.bookstore.serviceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.domain.User;
import com.bookstore.domain.UserBilling;
import com.bookstore.domain.UserPayment;
import com.bookstore.repository.BillingRepository;
import com.bookstore.repository.PaymentRepository;
import com.bookstore.repository.UserRepository;
import com.bookstore.service.PaymentService;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService{

	private static final Logger LOG=LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private PaymentRepository paymentRepository;
	
    @Autowired
    BillingRepository billingRepository;
    
    @Autowired
    UserRepository userRepository;
    

	@Override
	public UserPayment findById(Long id) {
		return paymentRepository.findUserPaymentById(id) ;
	}

	@Override
	public void removePaymentById(Long id) {
		paymentRepository.removeById(id);
	}

	@Override
	public void setDefaultCard(Long id, User user) {
		List<UserPayment> listUserPayment = user.getUserPaymentList();
		for(UserPayment eachPayment: listUserPayment){
			if(eachPayment.getId() == id) {
				eachPayment.setDefaultPayment(true);
			}
			else {
				eachPayment.setDefaultPayment(false);
			}
			paymentRepository.save(eachPayment);
		}
	}
	
	@Override
	public void updateExistingUserPaymentInfo(User user, UserBilling userBilling, UserPayment userPayment) {
        userRepository.save(user);
        billingRepository.save(userBilling);
        paymentRepository.save(userPayment);
	}
	
	@Override
	public void updateUserPayment(User user, UserBilling userBilling, UserPayment userPayment) {
		userBilling.setUserPayment(userPayment);
		userPayment.setUserBilling(userBilling);
		userPayment.setUser(user);
        List<UserPayment> paymentList = user.getUserPaymentList();
      
        if(userPayment.getId() != null ) {
        	updateExistingUserPaymentInfo(user,userBilling,userPayment);
        }
        else{
        	userPayment.setDefaultPayment(true);
        	for(UserPayment eachPaymnet:paymentList) {
        			eachPaymnet.setDefaultPayment(false);
        	}    
        	user.getUserPaymentList().add(userPayment);
        	userRepository.save(user);
        }
		
	}

	
	
}
