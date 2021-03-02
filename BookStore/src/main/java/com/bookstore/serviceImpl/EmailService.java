package com.bookstore.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.springframework.mock.web.MockMultipartFile;

import com.bookstore.domain.CartItem;
import com.bookstore.domain.Order;
import com.bookstore.domain.OrderEmailCartBean;
import com.bookstore.domain.User;
import com.bookstore.domain.UserShipping;

@Service
public class EmailService {

	private static final Logger log = LoggerFactory.getLogger(EmailService.class);
	
    private final TemplateEngine templateEngine;

    private final JavaMailSender javaMailSender;

    public EmailService(TemplateEngine templateEngine, JavaMailSender javaMailSender) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
    }

    public String sendOrderConfirmation(Order order,User user,UserShipping shipping,List<CartItem> cartList) throws MessagingException {
        Context context = new Context();
        context.setVariable("name", user.getFirstName());
        context.setVariable("order", order);
        context.setVariable("shipping",shipping);
        
        List<OrderEmailCartBean> bookList = new ArrayList<OrderEmailCartBean>();
        for(CartItem eachItem:cartList) {
        	  OrderEmailCartBean cartData = new OrderEmailCartBean();
        	  cartData.setTitle(eachItem.getBook().getTitle());
        	  cartData.setQty(eachItem.getQty());
        	  cartData.setOurPrice(eachItem.getBook().getOurPrice());
        	  cartData.setSubtotal(eachItem.getSubtotal());
        	  bookList.add(cartData);
        }
        
        context.setVariable("BookList",bookList);
        
        MultipartFile result = null;
        try {
        String logoPath = "images/logo.png";
        String name = "logo.png";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(logoPath).getFile());
        byte[] content =  Files.readAllBytes(file.toPath());
        result = new MockMultipartFile("logo",name,"image/png", content);
        context.setVariable("imageResourceName",result.getName());
        System.out.println("File Found : " + file.exists());
        }
        catch(Exception e) {
  		  log.error("Error in email service"+e.getMessage()); 
        }
        
        String htmlContent = templateEngine.process("emails/order-confirmation", context);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        
        helper.setSubject("Order confirmation for Order id- "+order.getId());
        helper.setText(htmlContent, true);
        helper.setTo(user.getEmail());
		
		  InputStreamSource imageSource = null;
		  try {
			  imageSource = new ByteArrayResource(result.getBytes());
			  }
		  catch (Exception e) {
			  log.error("Error in email service"+e.getMessage()); 
		  }
		    
         helper.addInline(result.getName(), imageSource, result.getContentType());
        
        javaMailSender.send(mimeMessage);
        return "Sent";
    }
    
    public String sendPassword(User user,String password) throws MessagingException {
        Context context = new Context();
        context.setVariable("username", user.getUsername());
        context.setVariable("password", password);
        
        MultipartFile result = null;
        try {
        String logoPath = "images/logo.png";
        String name = "logo.png";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(logoPath).getFile());
        byte[] content =  Files.readAllBytes(file.toPath());
        result = new MockMultipartFile("logo",name,"image/png", content);
        context.setVariable("imageResourceName",result.getName());
        System.out.println("File Found : " + file.exists());
        }
        catch(Exception e) {
  		  log.error("Error in email service"+e.getMessage()); 
        }
        
        String htmlContent = templateEngine.process("emails/credentials-email", context);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        
        helper.setSubject("BookStore new account ");
        helper.setText(htmlContent, true);
        helper.setTo(user.getEmail());
		
		  InputStreamSource imageSource = null;
		  try {
			  imageSource = new ByteArrayResource(result.getBytes());
			  }
		  catch (Exception e) {
			  log.error("Error in email service"+e.getMessage()); 
		  }
		    
         helper.addInline(result.getName(), imageSource, result.getContentType());
        
        javaMailSender.send(mimeMessage);
        return "Sent";
    }
}