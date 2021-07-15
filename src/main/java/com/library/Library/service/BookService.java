package com.library.Library.service;

import com.library.Library.dto.requests.BookCreateRequest;
import com.library.Library.dto.responses.BookResponse;
import com.library.Library.entity.Author;
import com.library.Library.entity.Book;
import com.library.Library.entity.BookGenre;
import com.library.Library.repository.AuthorRepository;
import com.library.Library.repository.BookGenreRepository;
import com.library.Library.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

//Try adding @Transactional over the service method where you are trying to access lob field object.
//When error "org.hibernate.HibernateException: Unable to access lob stream" happening
@Transactional
@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookGenreRepository bookGenreRepository;
    private final static Logger LOGGER = LoggerFactory
            .getLogger(EmailService.class);


    public ResponseEntity<Book> addBook(BookCreateRequest request) {
        boolean isBookExist = bookRepository
                .findBookByTitle(request.getTitle())
                .isPresent();
        if (isBookExist)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This book is already in library");
        else {
            Author author;
            BookGenre bookGenre;
            Boolean isAuthorExists = authorRepository.existsAuthorByFullName(request.getAuthor());
            Boolean isBookGenreExists = bookGenreRepository.existsBookGenreByTitle(request.getBookGenre());
            LOGGER.error(isAuthorExists + " " + isBookGenreExists);
            if (!isAuthorExists) {
                author = new Author(request.getAuthor());
                authorRepository.save(author);
            }else{
                author = authorRepository.findAuthorByFullName(request.getAuthor());
            }
            if (!isBookGenreExists) {
                bookGenre = new BookGenre(request.getBookGenre());
                bookGenreRepository.save(bookGenre);
            }else{
                bookGenre = bookGenreRepository.findBookGenreByTitle(request.getBookGenre());
            }

            Book book = new Book(
                    bookGenre,
                    author,
                    request.getTitle(),
                    request.getReleaseDate(),
                    request.getDescription(),
                    request.getScore(),
                    request.getPrice(),
                    request.getThumbnail()
            );
            return new ResponseEntity<>(bookRepository.save(book), HttpStatus.CREATED);
        }
    }

    public ResponseEntity<Map<String, Object>> findAllBooks(int page, int size) {
        try {
            Pageable paging = PageRequest.of(page, size);

            Page<Book> paginationBooks = bookRepository.findAll(paging);
            List<Book> books = paginationBooks.getContent();
            List<BookResponse> bookResponses = new ArrayList<>();
            for (Book book : books){
                BookResponse bookResponse = new BookResponse(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor().getFullName(),
                        book.getBookGenre().getTitle(),
                        book.getDescription(),
                        book.getScore(),
                        book.getPrice(),
                        book.getReleaseDate(),
                        book.getThumbnail()
                );
                bookResponses.add(bookResponse);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("data", bookResponses);
            response.put("currentPage", paginationBooks.getNumber());
            response.put("totalItems", paginationBooks.getTotalElements());
            response.put("totalPages", paginationBooks.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Book> updateBook(BookCreateRequest book, String id) {
        Book oldInfo = bookRepository.getById(id);
        boolean isBookExist = bookRepository
                .findBookById(id)
                .isPresent();
        if (!isBookExist) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This book is not in library");
        oldInfo.setTitle(book.getTitle());
        oldInfo.setDescription(book.getDescription());
        oldInfo.setScore(book.getScore());
        oldInfo.setReleaseDate(book.getReleaseDate());
        oldInfo.setPrice(book.getPrice());
        oldInfo.setThumbnail(book.getThumbnail());
        return new ResponseEntity<>(bookRepository.save(oldInfo), HttpStatus.OK);
    }

    public ResponseEntity<BookResponse> findBookById(String id) {
        Optional<Book> book = bookRepository.findBookById(id);
        if (!book.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no book found with above id");
        }
        BookResponse response = new BookResponse(
                book.get().getId(),
                book.get().getTitle(),
                book.get().getAuthor().getFullName(),
                book.get().getBookGenre().getTitle(),
                book.get().getDescription(),
                book.get().getScore(),
                book.get().getPrice(),
                book.get().getReleaseDate(),
                book.get().getThumbnail()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public void deleteBook(String id) {
        if (!bookRepository.findBookById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no book found with above id");
        }
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
