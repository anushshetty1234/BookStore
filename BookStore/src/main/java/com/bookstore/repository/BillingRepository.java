package com.bookstore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.domain.UserBilling;


@Repository
public interface BillingRepository extends CrudRepository<UserBilling, Long> {

}
