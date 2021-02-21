package com.bookstore.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.domain.Order;
import com.bookstore.domain.User;
import com.bookstore.domain.UserBilling;
import com.bookstore.domain.UserPayment;
import com.bookstore.domain.UserShipping;
import com.bookstore.domain.security.Role;
import com.bookstore.domain.security.UserRole;
import com.bookstore.repository.BillingRepository;
import com.bookstore.repository.PaymentRepository;
import com.bookstore.repository.RoleRepository;
import com.bookstore.repository.UserRepository;
import com.bookstore.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOG=LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Transactional
	@Override
	public User CreateUser(User user, Set<UserRole> userRoles) {
		User localUser=userRepository.findByUserName(user.getUserName());
		
		if(localUser!=null) {
			LOG.warn("Username {} already exists",user.getUserName());
		}
		else {
			  for(UserRole userRole:userRoles) { 
				  Role role=roleRepository.save(userRole.getRole());//capturing role which was persisted
				  userRole.setRole(role);                           // transisent role replaced with persisted role
			
			  }
			  
			  user.getUserRoles().addAll(userRoles);
			  
			  user.setUserPaymentList(new ArrayList<UserPayment>());
			  user.setUserShippingList(new ArrayList<UserShipping>());
			  user.setOrder(new ArrayList<Order>());
			  
			localUser=userRepository.save(user);
		}
		
		return  localUser;
	}

	public User findUserByUsername(String username) {
		return userRepository.findByUserName(username);
	}
	
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public User findById(Long id) {
		return userRepository.findUserById(id);
	}	
	
}
