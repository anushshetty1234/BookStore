package com.bookstore.serviceImpl;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.domain.Book;
import com.bookstore.repository.BookRepository;
import com.bookstore.service.BookService;

@Service
public class BookServiceImpl implements BookService{

	@Autowired
	BookRepository bookRepo;
	
	@Override
	public List<Book> findAllBook() {
		List<Book> allBook=(List<Book>) bookRepo.findAll();
		
		List<Book> activeBook= new ArrayList<>();
		
		for(Book book:allBook) {
			if (book.isActive()) {
				activeBook.add(book);
			}
		}
		
		return activeBook;
	}

	@Override
	public Book save(Book book) {
		return bookRepo.save(book);
	}

	@Override
	public Book findById(Long id) {
		return bookRepo.findBookById(id);
	}

	@Override
	public List<Book> blurrySearch(String title) {
		List<Book> allBook=(List<Book>) bookRepo.findByTitleContaining(title);
		
		List<Book> activeBook= new ArrayList<>();
		
		for(Book book:allBook) {
			if (book.isActive()) {
				activeBook.add(book);
			}
		}
		
		return activeBook;
	}

	@Override
	public void removeBook(Long id) {
		bookRepo.deleteById(id);
	}

	@Override
	public List<Book> searchBooksWithKeyword(String keyword) {
		return bookRepo.filterBookListUsingKeyword(keyword);
	}
	

	
	
}
