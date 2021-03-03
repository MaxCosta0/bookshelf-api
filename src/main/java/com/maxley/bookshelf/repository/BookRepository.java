package com.maxley.bookshelf.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxley.bookshelf.domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	List<Book> findByName(String name);
	Optional<Book> findByisbn(String isbn);
}
