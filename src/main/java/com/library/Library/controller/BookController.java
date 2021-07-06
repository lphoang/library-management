package com.library.Library.controller;

import com.library.Library.entity.Book;
import com.library.Library.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable String id) {
        Book book = bookService.findBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.findAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book newBook = bookService.addBook(book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Book> updateBookDetails(@RequestBody Book book, @PathVariable String id) {
        Book newBookDetails = bookService.updateBook(book, id);
        return new ResponseEntity<>(newBookDetails, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(params = "title")
    public ResponseEntity<List<Book>> getBooksByTitle(@RequestParam String title) {
        return new ResponseEntity<>(bookService.findBooksByTitle(title), HttpStatus.OK);
    }

    @GetMapping(params = "author")
    public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam String author) {
        return new ResponseEntity<>(bookService.findBooksByAuthor(author), HttpStatus.OK);
    }

    @GetMapping(params = "bookGenre")
    public ResponseEntity<List<Book>> getBooksByBookGenre(@RequestParam String bookGenre) {
        return new ResponseEntity<>(bookService.findBooksByBookGenre(bookGenre), HttpStatus.OK);
    }

    @GetMapping(params = {"lowScore", "highScore"})
    public ResponseEntity<List<Book>> getBooksByScore(@RequestParam Double lowScore, @RequestParam Double highScore) {
        return new ResponseEntity<>(bookService.findBooksByScore(lowScore, highScore), HttpStatus.OK);
    }

    @GetMapping(params = {"lowPrice", "highPrice"})
    public ResponseEntity<List<Book>> getBooksByPrice(@RequestParam Double lowPrice, @RequestParam Double highPrice) {
        return new ResponseEntity<>(bookService.findBooksByPrice(lowPrice, highPrice), HttpStatus.OK);
    }

//    @GetMapping(params = {"title", "author", "bookGenre", "lowScore", "highScore", "lowPrice", "highPrice"})
//    public ResponseEntity<List<Book>> getBooksByProps(
//            @RequestParam String title,
//            @RequestParam String author,
//            @RequestParam String bookGenre,
//            @RequestParam Double lowScore,
//            @RequestParam Double highScore,
//            @RequestParam Double lowPrice,
//            @RequestParam Double highPrice) {
//        return new ResponseEntity<>(bookService
//                .findBooksByProps(title, author, bookGenre, lowScore, highScore, lowPrice, highPrice),
//                HttpStatus.OK);
//    }
}
