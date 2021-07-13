package com.library.Library.repository;

import com.library.Library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, String> {
    Optional<Author> findAuthorById(String id);
    Optional<Author> findAuthorByFullName(String fullName);

    Boolean existsAuthorByFullName(String fullName);
}
