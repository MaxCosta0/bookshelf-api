package com.maxley.bookshelf.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.maxley.bookshelf.domain.Book;
import com.maxley.bookshelf.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookService bookService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Book postBook(@Valid @RequestBody Book book) {
		return bookService.saveBook(book);
	}
	
	@GetMapping
	public List<Book> getAllBooks(){
		return bookService.findAllBooks();
	}
	
	@GetMapping("/{bookName")
	public List<Book> getBookByName(@Valid @PathVariable String bookName){
		return bookService.findBookByName(bookName);
	}
	
	@GetMapping("/{bookISBN}")
	public ResponseEntity<Book> getBookByISBN(@Valid @PathVariable String bookISBN) {
		Optional<Book> foundBook = bookService.findBookByISBN(bookISBN);
		
		if(foundBook.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(foundBook.get());
	}
	
	@PutMapping("/{bookISBN}")
	public ResponseEntity<Book> putBook(@Valid @RequestBody Book newBook, @PathVariable String bookISBN){
		Optional<Book> foundBook = bookService.findBookByISBN(bookISBN);
		
		if(foundBook.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		newBook.setId(foundBook.get().getId());
		bookService.saveBook(newBook);
		
		return ResponseEntity.ok(newBook);
		
	}
	
	@DeleteMapping("/{bookId}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long id){
		Boolean success = bookService.deleteBook(id);
		
		if(!success) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
}
