package com.maxley.bookshelf.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxley.bookshelf.domain.Author;
import com.maxley.bookshelf.domain.Book;
import com.maxley.bookshelf.exception.DomainException;
import com.maxley.bookshelf.repository.BookRepository;

@Service
public class BookService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private AuthorService authorService;
	
	public Book saveBook(Book book) {
		Optional<Book> foundBook = bookRepository.findByisbn(book.getIsbn());
		
		if(foundBook.isPresent() && !book.equals(foundBook.get())) {
			throw new DomainException("This book has already been registerd!");
		}
		
		Author authorSaved = authorService.saveAuthor(book.getAuthor());
		
		Book newBook = new Book(null, book.getName(), book.getIsbn(), authorSaved);
		
		return bookRepository.save(newBook);
	}
	
	public List<Book> findBookByName(String name){
		return bookRepository.findByName(name);
	}
	
	public List<Book> findAllBooks(){
		return bookRepository.findAll();
	}
	
	public Optional<Book> findBookByISBN(String isbn) {
		return bookRepository.findByisbn(isbn);
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
