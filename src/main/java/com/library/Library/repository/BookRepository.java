package com.library.Library.repository;

import com.library.Library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, String> {
    void deleteBookById(String id);
    Optional<Book> findBookById(String id);
    Optional<Book> findBookByTitle(String title);
}
