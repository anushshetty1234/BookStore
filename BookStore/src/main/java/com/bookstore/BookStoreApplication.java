package com.bookstore;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bookstore.config.SecurityUtility;
import com.bookstore.domain.User;
import com.bookstore.domain.security.Role;
import com.bookstore.domain.security.UserRole;
import com.bookstore.service.UserService;

@SpringBootApplication
public class BookStoreApplication implements CommandLineRunner {

	@Autowired
	UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(BookStoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		  User user1=new User();
		  user1.setUserName("test");
		  user1.setPassword(SecurityUtility.passwordEncoder().encode("test"));
		  user1.setEmail("test@test.com");
		  user1.setEnabled(true);
		  user1.setFirstName("testFirstName");
		  user1.setLastName("testLastName");
		  user1.setPhone("123456789"); 
		  Role role1=new Role();
		  role1.setRoleId((long)1); 
		  role1.setRoleName("ROLE_USER");
		  UserRole userRole1=new UserRole(user1,role1);
		  Set<UserRole> userRoles1=new HashSet<UserRole>();
		  userRoles1.add(userRole1); 
		  userService.CreateUser(user1, userRoles1);
		  
		  User user2=new User(); 
		  user2.setUserName("admin");
		  user2.setPassword(SecurityUtility.passwordEncoder().encode("admin"));
		  user2.setEmail("admin@admin.com");
		  user2.setEnabled(true);
		  user2.setFirstName("adminFirstName"); 
		  user2.setLastName("adminLastName");
		  user2.setPhone("223456789");
		  Role role2=new Role();
		  role2.setRoleId((long) 2);
		  role2.setRoleName("ROLE_ADMIN"); 
		  UserRole userRole2=new UserRole(user2,role2);
		  Set<UserRole> userRoles2=new HashSet<UserRole>();
		  userRoles2.add(userRole2);
		  userService.CreateUser(user2, userRoles2);
		 
	}
	
	

}
