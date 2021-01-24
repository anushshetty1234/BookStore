package com.bookstore.service;

import com.bookstore.domain.User;
import com.bookstore.domain.UserShipping;

public interface ShippingService {

	void saveShipping(UserShipping userShipping);
	void addNewShippingToUser(UserShipping userShipping,User user);
	void removeShippingData(Long id);
	void setDefaultShippingData(Long id,User user);
}
