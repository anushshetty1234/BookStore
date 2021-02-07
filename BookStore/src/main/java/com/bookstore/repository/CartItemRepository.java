package com.bookstore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.domain.CartItem;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem, Long>{

	CartItem findCartItemById(Long id);
	
}
