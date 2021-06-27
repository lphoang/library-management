package com.library.Library.repository;

import com.library.Library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    void deleteBookById(Long id);
    Optional<Book> findBookById(Long id);
}
