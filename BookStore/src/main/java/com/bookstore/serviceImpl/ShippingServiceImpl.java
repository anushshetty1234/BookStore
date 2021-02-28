package com.bookstore.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.domain.User;
import com.bookstore.domain.UserShipping;
import com.bookstore.repository.ShippingRepository;
import com.bookstore.service.ShippingService;

@Service
@Transactional
public class ShippingServiceImpl implements ShippingService{

	@Autowired
	ShippingRepository shippingRepo;
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Override
	public void saveShipping(UserShipping userShipping) {
		shippingRepo.save(userShipping);
	}

	public UserShipping findUserShipping(Long id) {
		return shippingRepo.findUserShippingById(id);
	}
	
	@Override
	public void addNewShippingToUser(UserShipping userShipping, User user) {
		userShipping.setUser(user);
		if(userShipping.getId() != null) {
			updateExistingShipping(userShipping,user);
		}
		else {
			userShipping.setUserShippingDefault(true);
			List<UserShipping> shippingList = user.getUserShippingList();
			for(UserShipping eachShipping:shippingList) {
				eachShipping.setUserShippingDefault(false);
			}	
			user.getUserShippingList().add(userShipping);
			userServiceImpl.save(user);
		}
	}

	@Override
	public void removeShippingData(Long id) {
		shippingRepo.removeById(id);
	}

	@Override
	public void setDefaultShippingData(Long id,User user) {
		List<UserShipping> listUserShipping = user.getUserShippingList();
		for(UserShipping eachShipping: listUserShipping){
			if(eachShipping.getId() == id) {
				eachShipping.setUserShippingDefault(true);
			}
			else {
				eachShipping.setUserShippingDefault(false);
			}
			shippingRepo.save(eachShipping);
		}
	}

	@Override
	public void updateExistingShipping(UserShipping userShipping, User user) {
		userShipping.setUser(user);
		shippingRepo.save(userShipping);
		userServiceImpl.save(user);
	}
	
	
	
	
}
