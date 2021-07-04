package com.library.Library.controller;

import com.library.Library.entity.Book;
import com.library.Library.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable String id){
        Book book = bookService.findBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> books = bookService.findAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        Book newBook = bookService.addBook(book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    // TODO Change /update to update/{id}
    @PutMapping("/update")
    public ResponseEntity<Book> updateBookDetails(@RequestBody Book book){
        Book newBookDetails = bookService.updateBook(book);
        return new ResponseEntity<>(newBookDetails, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable String id){
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
