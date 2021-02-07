package com.bookstore.service;

import com.bookstore.domain.CartItem;
import com.bookstore.domain.ShoppingCart;

public interface CartItemService {

	CartItem createCartItem(Long bookId,int quantity,ShoppingCart cart);
	void addItemToCart(ShoppingCart cart,CartItem item);
	void updateCartItem(ShoppingCart cart,Long itemId,int quantity);
	void removeCartItem(ShoppingCart cart,Long itemId);
	
}
