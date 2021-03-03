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

import com.maxley.bookshelf.domain.Author;
import com.maxley.bookshelf.service.AuthorService;

@RestController
@RequestMapping("/authors")
public class AuthorController {

	@Autowired
	private AuthorService authorService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Author postAuthor(@Valid @RequestBody Author author) {
		return authorService.saveAuthor(author);
	}
	
	@GetMapping
	public List<Author> getAllAuthors(){
		return authorService.findAllAuthors();
	}
	
	@GetMapping("/{authorName}")
	public ResponseEntity<Author> getOneAuthor(@Valid @PathVariable String authorName) {
		Optional<Author> author = authorService.findOneAuthor(authorName);
		
		if(author.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(author.get());
		
	}
	
	@PutMapping("/{authorName}")
	public ResponseEntity<Author> putAuthor(@Valid @RequestBody Author newAuthor, @PathVariable String authorName) {
		Optional<Author> foundAuthor = authorService.findOneAuthor(authorName);
		
		if(foundAuthor.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		newAuthor.setId(foundAuthor.get().getId());
		authorService.saveAuthor(newAuthor);
		
		return ResponseEntity.ok(newAuthor);
	}
	
	@DeleteMapping("/{authorName}")
	public ResponseEntity<Void> deleteAuthor(@Valid @PathVariable String authorName){
		Boolean success = authorService.deleteAuthor(authorName);
		
		if(!success) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
}
