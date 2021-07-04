package com.library.Library.repository;

import com.library.Library.entity.BookGenre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookGenreRepository extends JpaRepository<BookGenre, String> {
    Optional<BookGenre> findGenreByTitle (String title);
}
