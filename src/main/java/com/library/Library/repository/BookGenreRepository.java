package com.library.Library.repository;

import com.library.Library.entity.BookGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookGenreRepository extends JpaRepository<BookGenre, String> {
    Optional<BookGenre> findGenreByTitle (String title);
    Optional<BookGenre> findGenreById(String id);
}
