package com.juank.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juank.entity.Book;
import com.juank.service.BookService;
import com.juank.service.googleBookService.GoogleBookService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/book")
public class BookControllerV1 {
	
	Logger logger = LoggerFactory.getLogger(BookControllerV1.class);
	
	public static final String TRACE = "trace";
		
	@Autowired
	BookService repo;
	
	@Autowired 
	GoogleBookService googleRepo;
	
	@GetMapping
	public ResponseEntity<List<Book>> getBooks(){
		return ResponseEntity.ok(repo.getBooks());
	}
	
	@CrossOrigin(origins = {"http://localhost:3000"})
	@GetMapping("/hello")
	public ResponseEntity<String>  hello() {
		return new ResponseEntity<>("hello", HttpStatus.OK);
	}
	
	@GetMapping("/{pageNumber}/{pageSize}")
	public List<Book> getBookPagination(Integer pageNumber, Integer pageSize, String sort){
		return repo.getPageList(pageNumber, pageSize, sort);
	}
	
	@PostMapping
	public ResponseEntity<String> saveBook(@RequestBody Book book) {
		repo.saveBook(book);
		return new ResponseEntity<>("Book Created", HttpStatus.OK);
	}
	
	@CrossOrigin(origins = {"http://localhost:3000"})
	@PostMapping("/google")
	@CircuitBreaker(name = "books-service", fallbackMethod="getDefaultBooks")
	public ResponseEntity<List<Book>> getUserBooks(@RequestBody Map<String, String> filters){
		List<Book> books;
		books = googleRepo.findByFilters(filters);
		return new ResponseEntity<>(books, HttpStatus.OK);
		
	}
	
	public ResponseEntity<List<Book>> getDefaultBooks(){
		return new ResponseEntity<>( new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}
	
}


