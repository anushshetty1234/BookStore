package com.bookstore.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.domain.UserPayment;

@Repository
public interface PaymentRepository extends CrudRepository<UserPayment, Long>{

	UserPayment findUserPaymentById(Long id);
	void removeById(Long Id);
	List<UserPayment> findAll();
	
}
