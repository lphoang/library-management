package com.library.Library.service;

import com.library.Library.entity.Author;
import com.library.Library.entity.Book;
import com.library.Library.entity.BookGenre;
import com.library.Library.exception.BookNotFoundException;
import com.library.Library.repository.AuthorRepository;
import com.library.Library.repository.BookGenreRepository;
import com.library.Library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//Try adding @Transactional over the service method where you are trying to access lob field object.
//When error "org.hibernate.HibernateException: Unable to access lob stream" happening
@Transactional
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookGenreRepository bookGenreRepository;

    @Autowired
    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository,
                       BookGenreRepository bookGenreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookGenreRepository = bookGenreRepository;
    }


    public Book addBook(Book book) {
        boolean isBookExist = bookRepository
                .findBookByTitle(book.getTitle())
                .isPresent();
        if (isBookExist) throw new IllegalStateException("This book is already in library");

        boolean isAuthorExist = authorRepository
                .findAuthorByFullName(book.getAuthor())
                .isPresent();

        if (!isAuthorExist) {
            Author author = new Author(
                    book.getAuthor()
            );
            authorRepository.save(author);
        }

        boolean isGenreExist = bookGenreRepository
                .findGenreByTitle(book.getBookGenre())
                .isPresent();

        if (!isGenreExist) {
            BookGenre bookGenre = new BookGenre(
                    book.getBookGenre()
            );
            bookGenreRepository.save(bookGenre);
        }

        return bookRepository.save(book);
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book updateBook(Book book, String id) {
        Book oldInfo = bookRepository.getById(id);
        boolean isBookExist = bookRepository
                .findBookById(id)
                .isPresent();
        if (!isBookExist) throw new IllegalStateException("This book is not in library");
        oldInfo.setTitle(book.getTitle());
        oldInfo.setDescription(book.getDescription());
        oldInfo.setScore(book.getScore());
        oldInfo.setReleaseDate(book.getReleaseDate());
        oldInfo.setPrice(book.getPrice());
        oldInfo.setThumbnail(book.getThumbnail());
        return bookRepository.save(oldInfo);
    }

    public Book findBookById(String id) {
        return bookRepository.findBookById(id)
                .orElseThrow(() -> new BookNotFoundException("Book by id = " + id + " was not found"));
    }

    public void deleteBook(String id) {
        bookRepository.deleteBookById(id);
    }

    public List<Book> findBooksByTitle(String title) {
        return bookRepository.findBooksByTitle(title);
    }

    public List<Book> findBooksByAuthor(String author) {
        return bookRepository.findBooksByAuthor(author);
    }

    public List<Book> findBooksByBookGenre(String bookGenre) {
        return bookRepository.findBooksByBookGenre(bookGenre);
    }

    public List<Book> findBooksByScore(Double lowScore, Double highScore) {
        return bookRepository.findBooksByScore(lowScore, highScore);
    }

    public List<Book> findBooksByPrice(Double lowPrice, Double highPrice) {
        return bookRepository.findBooksByPrice(lowPrice, highPrice);
    }

//    public List<Book> findBooksByProps(
//            String title,
//            String author,
//            String bookGenre,
//            Double lowScore,
//            Double highScore,
//            Double lowPrice,
//            Double highPrice
//    ) {
//        return bookRepository
//                .findBooksByProps(title, author, bookGenre, lowScore, highScore, lowPrice, highPrice);
//    }
}
