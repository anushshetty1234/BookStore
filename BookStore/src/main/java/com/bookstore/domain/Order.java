package com.bookstore.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user_order")
public class Order implements Serializable{
	
	private static final long serialVersionUID = 2893475845L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private LocalDateTime orderDate;
	private LocalDate shippingDate;
	private String shippingMethod;
	private String orderStatus;
	private BigDecimal orderTotal;
	
	@OneToMany(mappedBy = "order", cascade=CascadeType.ALL)
	private List<CartItem> cartItemList;
	
	@OneToOne
	@JoinColumn(name = "shipping_address")
	private UserShipping shippingAddress;
	
	@OneToOne
	@JoinColumn(name = "billing_address")
	private UserBilling billingAddress;
	
	@OneToOne
	@JoinColumn(name = "payment")
	private UserPayment payment;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public LocalDate getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(LocalDate shippingDate) {
		this.shippingDate = shippingDate;
	}

	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public BigDecimal getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(BigDecimal orderTotal) {
		this.orderTotal = orderTotal;
	}

	public List<CartItem> getCartItemList() {
		return cartItemList;
	}

	public void setCartItemList(List<CartItem> cartItemList) {
		this.cartItemList = cartItemList;
	}

	public UserShipping getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(UserShipping shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public UserBilling getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(UserBilling billingAddress) {
		this.billingAddress = billingAddress;
	}

	public UserPayment getPayment() {
		return payment;
	}

	public void setPayment(UserPayment payment) {
		this.payment = payment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
