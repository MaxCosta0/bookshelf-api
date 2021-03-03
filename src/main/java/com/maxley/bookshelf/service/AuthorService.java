package com.maxley.bookshelf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxley.bookshelf.domain.Author;
import com.maxley.bookshelf.repository.AuthorRepository;

@Service
public class AuthorService {

	@Autowired
	private AuthorRepository authorRepository;
	
	public Author saveAuthor(Author author) {
		Optional<Author> foundAuthor = authorRepository.findByName(author.getName());
		
		if(foundAuthor.isPresent() && !author.equals(foundAuthor.get()) ) {
			return foundAuthor.get();
		}
		
		return authorRepository.save(author);
	}
	
	public Optional<Author> findOneAuthor(String name) {
		return authorRepository.findByName(name);
	}
	
	public List<Author> findAllAuthors(){
		return authorRepository.findAll();
	}
	
	public Boolean deleteAuthor(String name) {
		Optional<Author> authorToRemove = authorRepository.findByName(name);
		
		if(authorToRemove.isEmpty()) {
			return false;
		}
		
		authorRepository.delete(authorToRemove.get());
		return true;
	}
}
