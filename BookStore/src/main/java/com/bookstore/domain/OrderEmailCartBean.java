package com.bookstore.domain;

import java.math.BigDecimal;

public class OrderEmailCartBean {

	private String title;
	private double ourPrice;
	private int qty;
	private BigDecimal subtotal;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public double getOurPrice() {
		return ourPrice;
	}
	public void setOurPrice(double ourPrice) {
		this.ourPrice = ourPrice;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public BigDecimal getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}
	
	
	
}
