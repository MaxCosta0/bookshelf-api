package com.maxley.bookshelf.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxley.bookshelf.domain.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

	Optional<Author> findByName(String name);
}
