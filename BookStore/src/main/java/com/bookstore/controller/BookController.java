package com.bookstore.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bookstore.domain.Book;
import com.bookstore.service.BookService;

@RestController
@RequestMapping(value = "/book")
public class BookController {
	
	@Autowired
	BookService bookService;
	
	@RequestMapping(value = "/add")
	public Book addBook(@RequestBody Book book) {
		return bookService.save(book);
	}

	
	@RequestMapping(value = "/add/image", method = RequestMethod.POST)
	public ResponseEntity uploadImage(@RequestParam(name = "id") Long id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		try {

			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Iterator<String> imageNames = multipartRequest.getFileNames();
			System.out.println(imageNames);
			MultipartFile imageMutipart = multipartRequest.getFile(imageNames.next());
			byte[] imageBytes = imageMutipart.getBytes();

			String imageNameNew = id + ".png";

			BufferedOutputStream bout = new BufferedOutputStream(
					new FileOutputStream(new File("src/main/resources/static/images/" + imageNameNew)));
			bout.write(imageBytes);
			bout.flush();
			bout.close();
			return new ResponseEntity("Upload success", HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity("Upload failed", HttpStatus.BAD_REQUEST);
		}

	}

	
}
