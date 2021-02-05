package com.bookstore.service;

import java.util.List;

import com.bookstore.domain.Book;


public interface BookService {
	
	List<Book> findAllBook();
	Book save(Book book);
	Book findById(Long id);
	List<Book> blurrySearch(String title);
	void removeBook(Long id);
	List<Book> searchBooksWithKeyword(String keyword);
}
