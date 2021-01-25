package com.bookstore.service;

import java.util.Set;

import com.bookstore.domain.User;
import com.bookstore.domain.UserBilling;
import com.bookstore.domain.UserPayment;
import com.bookstore.domain.security.UserRole;

public interface UserService {

	User CreateUser(User user,Set<UserRole> userRoles);
	public User findUserByUsername(String username);
	public User findUserByEmail(String email);
	public void save(User user);
	public User findById(Long id);
	
}
