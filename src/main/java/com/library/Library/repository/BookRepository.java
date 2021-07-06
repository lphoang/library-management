package com.library.Library.repository;

import com.library.Library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, String> {
    void deleteBookById(String id);

    Optional<Book> findBookById(String id);

    Optional<Book> findBookByTitle(String title);


    //Search
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE %?1%")
    List<Book> findBooksByTitle(String title);

    @Query("SELECT b FROM Book b WHERE b.author = ?1 OR LOWER(b.author) LIKE %?1%")
    List<Book> findBooksByAuthor(String author);

    @Query("SELECT b FROM Book b WHERE b.bookGenre = ?1 OR LOWER(b.bookGenre) LIKE %?1%")
    List<Book> findBooksByBookGenre(String bookGenre);

    @Query("SELECT b FROM Book b WHERE b.price > ?1 AND b.score < ?2")
    List<Book> findBooksByPrice(Double lowPrice, Double highPrice);

    @Query("SELECT b FROM Book b WHERE b.score > ?1 AND b.score < ?2")
    List<Book> findBooksByScore(Double lowScore, Double highScore);

//    @Query("SELECT b FROM Book b " +
//            "WHERE LOWER(b.title) LIKE %?1% " +
//            "OR LOWER(b.author) LIKE %?2% " +
//            "OR LOWER(b.bookGenre) LIKE %?3%" +
//            "OR b.score > ?4 AND b.score < ?5" +
//            "OR b.price > ?6 AND b.price < ?7")
//    List<Book> findBooksByProps(
//            String title,
//            String author,
//            String bookGenre,
//            Double lowScore,
//            Double highScore,
//            Double lowPrice,
//            Double highPrice);
}
