package com.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bookstore.domain.Book;

public interface BookRepository extends CrudRepository<Book, Long>{

	void deleteById(Long id);
	Book findBookById(Long id);
	List<Book> findByTitleContaining(String title);
	
	@Query("Select b from Book b where b.title like %:keyboard%")
	List<Book> filterBookListUsingKeyword(@Param("keyboard")String keyword);
	
}
