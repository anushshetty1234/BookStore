package com.bookstore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.domain.UserShipping;

@Repository
public interface ShippingRepository extends CrudRepository<UserShipping, Long> {

	void removeById(Long id);
	
}
