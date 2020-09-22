package com.bookstore.service;

import java.util.Set;

import com.bookstore.domain.User;
import com.bookstore.domain.security.UserRole;

public interface UserService {

	User CreateUser(User user,Set<UserRole> userRoles);
	
}
