package com.bookstore.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.domain.CartItem;
import com.bookstore.domain.ShoppingCart;
import com.bookstore.domain.User;
import com.bookstore.repository.CartItemRepository;
import com.bookstore.repository.ShoppingCartRepository;
import com.bookstore.service.ShoppingCartService;

@Service
@Transactional
public class ShopingCartServiceImpl implements ShoppingCartService{

	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Autowired
	ShoppingCartRepository cartRepo;
	
	@Autowired
	CartItemRepository cartItemRepo;
	
	
	
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
	
	
	public void clearShoppingCart(ShoppingCart cart) {
		if(cart != null) {
			List<CartItem> cartItems = cart.getCartItemList();
			
			if(cartItems !=null && cartItems.size() > 0) {
				for(CartItem eachItem:cartItems) {
					eachItem.setShoppingCart(null);
					cartItemRepo.save(eachItem);
				}
			}
			cart.setCartItemList(new ArrayList<CartItem>());
			cart.setGrandTotal(new BigDecimal(0));
			cartRepo.save(cart);
		}
	}
	
	
	

}
