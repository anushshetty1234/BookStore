package com.bookstore.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.domain.CartItem;
import com.bookstore.domain.ShoppingCart;
import com.bookstore.domain.User;
import com.bookstore.serviceImpl.CartItemServiceImpl;
import com.bookstore.serviceImpl.ShopingCartServiceImpl;
import com.bookstore.serviceImpl.UserServiceImpl;

@RestController
@RequestMapping(value = "/cart")
public class ShoppingCartController {

	private final static Logger LOG = LoggerFactory.getLogger(ShoppingCartController.class);
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Autowired
	ShopingCartServiceImpl shoppingCartServiceImpl;
	
	@Autowired
	CartItemServiceImpl cartItemServiceImpl;
	
	@RequestMapping(value = "/getShoppingCart")
	public ShoppingCart loadCart(Principal principal) {
		ShoppingCart cart = null;
		
		if(principal != null) {
			User user = userServiceImpl.findUserByUsername(principal.getName());
			if(user != null) {
				cart = shoppingCartServiceImpl.loadShoppngCartSerice(user);
			}
		}
		
		return cart;
	}
	
	@RequestMapping(value = "/getCartItems")
	public List<CartItem> getCartItems(Principal principal){
		
		if(principal != null) {
			User user = userServiceImpl.findUserByUsername(principal.getName());
			if(user != null && user.getShoppingCart() != null) {
				return user.getShoppingCart().getCartItemList();
			}
		}
		
		return null;
	}
	
	@RequestMapping(value = "/addICartItem")
	public ResponseEntity addItemToCart(@RequestBody HashMap<String,String> mapper,Principal principal) {
		
		if(principal != null) {
			User user = userServiceImpl.findUserByUsername(principal.getName());
			if(user != null) {
				try {
					ShoppingCart	cart = shoppingCartServiceImpl.loadShoppngCartSerice(user);
					Long bookId = Long.parseLong(mapper.get("bookId"));
					int quantity = Integer.parseInt(mapper.get("quantity"));
					LOG.error("Error 1");
					CartItem newCartItem =	cartItemServiceImpl.createCartItem(bookId,quantity,cart);
					LOG.error("Error 2");
					cartItemServiceImpl.addItemToCart(cart,newCartItem);
					LOG.error("Error 3");
					return new ResponseEntity("Added book to cart successfully",HttpStatus.OK);
				}
				catch(Exception e ) {
					LOG.error("Error occured while adding item to cart :"+e.getMessage() + e);
				}
			}
		}
		
		return new ResponseEntity("Faild to add book to cart!",HttpStatus.BAD_REQUEST);
	}
	
	
	@RequestMapping(value = "/updateItem")
	public ResponseEntity updateItemInCart(@RequestBody HashMap<String,String> mapper,Principal principal) {
		
		if(principal != null) {
			User user = userServiceImpl.findUserByUsername(principal.getName());
			if(user != null) {
				try {
					Long cartItemId = Long.parseLong(mapper.get("cartItemId"));
					int quantity = Integer.parseInt(mapper.get("quantity"));
					
					ShoppingCart	cart = shoppingCartServiceImpl.loadShoppngCartSerice(user);
					cartItemServiceImpl.updateCartItem(cart,cartItemId,quantity);
					return new ResponseEntity("Updated cart successfully",HttpStatus.OK);
				}
				catch(Exception e ) {
					LOG.error("Error occured while updating item in cart :"+e.getMessage() + e);
				}
			}
		}
		
		return new ResponseEntity("Faild to update book in cart!",HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/removeCartItem")
	public ResponseEntity removeItemInCart(@RequestBody String cartItemIdParam,Principal principal) {
		
		if(principal != null) {
			User user = userServiceImpl.findUserByUsername(principal.getName());
			if(user != null) {
				try {
					Long cartItemId = Long.parseLong(cartItemIdParam);
					ShoppingCart	cart = shoppingCartServiceImpl.loadShoppngCartSerice(user);
					cartItemServiceImpl.removeCartItem(cart,cartItemId);
					return new ResponseEntity("Updated cart successfully",HttpStatus.OK);
				}
				catch(Exception e ) {
					LOG.error("Error occured while updating item in cart :"+e.getMessage() + e);
				}
			}
		}
		
		return new ResponseEntity("Faild to update book in cart!",HttpStatus.BAD_REQUEST);
	}
	
}
