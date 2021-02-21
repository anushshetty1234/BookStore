package com.bookstore.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public String sendMail(Order order,User user,UserShipping shipping,List<CartItem> cartList) throws MessagingException {
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
        
		/*
		 * Path path = Paths.get("/images/logo.png"); String name = "logo.png"; byte[]
		 * content = null; try { content = Files.readAllBytes(path); } catch (final
		 * IOException e) { } MultipartFile result = new MockMultipartFile("logo",name,
		 * "image/png", content); context.setVariable("imageResourceName",
		 * result.getName());
		 */
        
        String htmlContent = templateEngine.process("emails/order-confirmation", context);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        
        helper.setSubject("Order confirmation for Order id- "+order.getId());
        helper.setText(htmlContent, true);
        helper.setTo("anushshetty75@gmail.com");
		/*
		 * InputStreamSource imageSource = null; try { imageSource = new
		 * ByteArrayResource(result.getBytes()); log.error("size="+result.getSize()); }
		 * catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */    
        //helper.addInline(result.getName(), imageSource, result.getContentType());
        
        javaMailSender.send(mimeMessage);
        return "Sent";
    }
}