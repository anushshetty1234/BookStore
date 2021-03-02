package com.bookstore.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.domain.CartItem;
import com.bookstore.domain.Order;
import com.bookstore.domain.ShoppingCart;
import com.bookstore.domain.User;
import com.bookstore.domain.UserBilling;
import com.bookstore.domain.UserPayment;
import com.bookstore.domain.UserShipping;
import com.bookstore.repository.CartItemRepository;
import com.bookstore.repository.OrderRepository;
import com.bookstore.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	OrderRepository orderRepo;
	
	@Autowired
	BillingServiceImpl billingServiceImpl;
	
	@Autowired
	ShippingServiceImpl shippingServiceImpl;
	
	@Autowired
	PaymentServiceImpl paymentServiceImpl;
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Autowired
	CartItemRepository cartItemRepo;
	
	@Autowired
	ShopingCartServiceImpl cartServiceImpl;
	
	public Order createOrder(String shippingMethod, UserShipping shippingAddress, UserBilling userBilling,
			UserPayment payment, ShoppingCart cart, User user) {
		
		Order order = null;
		try {
			
			UserPayment savedUserPayement;
			UserShipping savedUserShipping;
			UserBilling saveduserBilling;
			
			Long idPayment = payment.getId();
			Long idShipping = shippingAddress.getId();
			Long idBilling = userBilling.getId();
			
			if(idShipping != null) {
				savedUserShipping = shippingServiceImpl.findUserShipping(idShipping);
				myCopyProperties(shippingAddress,savedUserShipping);
				shippingServiceImpl.addNewShippingToUser(savedUserShipping, user);
			}
			else {
				savedUserShipping = shippingAddress;
				shippingServiceImpl.addNewShippingToUser(savedUserShipping, user);
			}
			
			if(idBilling != null) {
				saveduserBilling = billingServiceImpl.findUserBillingById(idBilling);
				myCopyProperties(userBilling,saveduserBilling);
			}
			else {
				saveduserBilling = userBilling;
			}
			if(idPayment != null) {
				savedUserPayement = paymentServiceImpl.findById(idPayment);
				myCopyProperties(payment,savedUserPayement);
				paymentServiceImpl.updateUserPayment(user, saveduserBilling, savedUserPayement);
			}
			else {		
				savedUserPayement = payment;
				paymentServiceImpl.updateUserPayment(user, saveduserBilling, savedUserPayement);
			}
					
			order = new Order();

			LocalDateTime timestamp = LocalDateTime.now();

			order.setBillingAddress(saveduserBilling);
			order.setPayment(savedUserPayement);
			order.setShippingAddress(savedUserShipping);
			order.setShippingMethod(shippingMethod);
			order.setCartItemList(cart.getCartItemList());
			order.setOrderTotal(cart.getGrandTotal());
			order.setOrderDate(timestamp);
			order.setUser(user);
			
			for(CartItem eachItem:cart.getCartItemList()) {
				eachItem.setOrder(order);
				cartItemRepo.save(eachItem);
			}

			LocalDate today = LocalDate.now();
			
			if (shippingMethod.equalsIgnoreCase("groundShipping")) {
				order.setShippingDate(today.plusDays(5));
			} else {
				order.setShippingDate(today.plusDays(3));
			}
			order.setOrderStatus("placed");
			
			order = orderRepo.save(order);
			userServiceImpl.save(user);
			
			cartServiceImpl.clearShoppingCart(cart);
		} catch (Exception e) {
			log.error("Error occured when persisting order " + e + e.getMessage());
		}
		return order;
	}

	public static String[] getNullPropertyNames (Object source) {
	    final BeanWrapper src = new BeanWrapperImpl(source);
	    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

	    Set<String> emptyNames = new HashSet<String>();
	    for(java.beans.PropertyDescriptor pd : pds) {
	        Object srcValue = src.getPropertyValue(pd.getName());
	        if (srcValue == null) emptyNames.add(pd.getName());
	    }

	    String[] result = new String[emptyNames.size()];
	    return emptyNames.toArray(result);
	}

	public static void myCopyProperties(Object src, Object target) {
	    BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}
	
	
}
