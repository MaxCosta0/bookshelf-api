package com.maxley.bookshelf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxley.bookshelf.domain.Author;
import com.maxley.bookshelf.domain.Book;
import com.maxley.bookshelf.exception.DomainException;
import com.maxley.bookshelf.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private AuthorService authorService;
	
	public Book saveBook(Book book) {
		Optional<Book> foundBook = bookRepository.findByISBN(book.getIsbn());
		
		if(foundBook.isPresent() && !book.equals(foundBook.get())) {
			throw new DomainException("This book is already registerd!");
		}
		
		for(Author author: book.getAuthors()) {
			authorService.saveAuthor(author);
		}
		
		return bookRepository.save(book);
	}
	
	public List<Book> findBookByName(String name){
		return bookRepository.findByName(name);
	}
	
	public List<Book> findAllBooks(){
		return bookRepository.findAll();
	}
	
	public Optional<Book> findBookByISBN(String isbn) {
		return bookRepository.findByISBN(isbn);
	}
	
	public Boolean deleteBook(Long id) {
		Optional<Book> bookToRemove = bookRepository.findById(id);
		
		if(bookToRemove.isEmpty()) {
			return false;
		}
		
		bookRepository.delete(bookToRemove.get());
		return true;
	}
	
}
