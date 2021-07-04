package com.library.Library.repository;

import com.library.Library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, String> {
    Optional<Author> findAuthorByFullName(String fullName);
}
