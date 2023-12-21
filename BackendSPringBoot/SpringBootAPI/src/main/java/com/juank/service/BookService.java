package com.juank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.juank.entity.Book;
import com.juank.jpa.BookRepository;

@Service
public class BookService {

	@Autowired
	BookRepository repo;
	
	public void saveBook(Book book) {
		repo.save(book);
	}
	
	public List<Book> getPageList(Integer pageNumber, Integer pageSize, String sort) {
		Pageable pageable = null;
		if(sort != null) {
			pageable = PageRequest.of(pageNumber, pageSize,Sort.Direction.ASC, sort);
		}else {
	      pageable = PageRequest.of(pageNumber, pageSize);
	    }
	    return repo.findAll(pageable).getContent();
	}
	
	public List<Book> getBooks() {
		return repo.findAll();
	}
}
