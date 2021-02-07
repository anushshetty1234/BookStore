package com.bookstore.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.domain.Book;
import com.bookstore.domain.CartItem;
import com.bookstore.domain.ShoppingCart;
import com.bookstore.repository.CartItemRepository;
import com.bookstore.repository.ShoppingCartRepository;
import com.bookstore.service.CartItemService;

@Service
@Transactional
public class CartItemServiceImpl implements CartItemService {

	private static final Logger LOG = LoggerFactory.getLogger(CartItemServiceImpl.class);
	
	@Autowired
	CartItemRepository cartItemRepo;
	
	@Autowired
	ShoppingCartRepository shoppingCartRepo;
	
	@Autowired
	BookServiceImpl boookServiceImpl;
	
	@Override
	public CartItem createCartItem(Long bookId, int quantity,ShoppingCart cart) {
		
		try {
		Book book = boookServiceImpl.findById(bookId);
		
		CartItem cartItem = new CartItem();
		cartItem.setQty(quantity);
		cartItem.setBook(book);
		cartItem.setShoppingCart(cart);
		BigDecimal qtyDecimal = new BigDecimal(quantity);
		BigDecimal priceDecimal = new BigDecimal(book.getOurPrice());
		cartItem.setSubtotal(qtyDecimal.multiply(priceDecimal));
		CartItem cartItemPersisted = cartItemRepo.save(cartItem);
		return cartItemPersisted;
		}
		catch(Exception e) {
			LOG.error("Failed to create cart Item:"+e.getMessage()+e);
		}
		return null;
	}

	@Override
	public void addItemToCart(ShoppingCart cart, CartItem item) {

		try {
			cart.getCartItemList().add(item);
			cart.setGrandTotal(cart.getGrandTotal().add(item.getSubtotal()));
			shoppingCartRepo.save(cart);
		}
		catch(Exception e) {
			LOG.error("Failed to add item to cart:"+e.getMessage()+e);
		}

	}

	@Override
	public void updateCartItem(ShoppingCart cart, Long itemId, int newQuantity) {
		try {
			CartItem cartItem =null;
			List<CartItem> cartItems = cart.getCartItemList();
			for(CartItem eachItem:cartItems) {
				if(eachItem.getId() == itemId) {
					cartItem = eachItem;
					break;
				}
			}
			int previousQuantity = cartItem.getQty();
			BigDecimal previousSubTotal = cartItem.getSubtotal();
			BigDecimal priceOfBook = previousSubTotal.divide(new BigDecimal(previousQuantity));
			cartItem.setQty(newQuantity);
			BigDecimal newSubTotal = priceOfBook.multiply(new BigDecimal(newQuantity));
			cartItem.setSubtotal(newSubTotal);
			cartItemRepo.save(cartItem);
			cart.setGrandTotal(cart.getGrandTotal().subtract(previousSubTotal).add(newSubTotal));
			shoppingCartRepo.save(cart);
		}
		catch(Exception e) {
			LOG.error("Failed to add item to cart:"+e.getMessage()+e);
		}
		
	}

	@Override
	public void removeCartItem(ShoppingCart cart, Long itemId) {
		try {
			CartItem cartItem =null;
			List<CartItem> cartItems = cart.getCartItemList();
			for(CartItem eachItem:cartItems) {
				if(eachItem.getId() == itemId) {
					cartItem = eachItem;
					break;
				}
			}
			BigDecimal subTotal = cartItem.getSubtotal();
			cart.setGrandTotal(cart.getGrandTotal().subtract(subTotal));
			cart.getCartItemList().remove(cartItem);
			cartItemRepo.deleteById(itemId);
			shoppingCartRepo.save(cart);
		}
		catch(Exception e) {
			LOG.error("Failed to add item to cart:"+e.getMessage()+e);
		}
		
	}

}
