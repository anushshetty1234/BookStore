package com.bookstore.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.domain.CartItem;
import com.bookstore.domain.ShoppingCart;
import com.bookstore.domain.User;
import com.bookstore.service.ShoppingCartService;

@Service
@Transactional
public class ShopingCartServiceImpl implements ShoppingCartService{

	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Override
	public ShoppingCart loadShoppngCartSerice(User user) {
		ShoppingCart cart = user.getShoppingCart();
		if(cart == null) {
			cart = new ShoppingCart();
			user.setShoppingCart(cart);
			cart.setUser(user);
			cart.setCartItemList(new ArrayList<CartItem>());
			cart.setGrandTotal(new BigDecimal(0));
			userServiceImpl.save(user);
		}
		return cart;
	}
	
	

}
