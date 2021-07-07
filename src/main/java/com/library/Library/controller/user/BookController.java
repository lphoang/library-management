package com.library.Library.controller.user;

import com.library.Library.entity.Book;
import com.library.Library.service.BookService;
import lombok.AllArgsConstructor;
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

    @GetMapping(value = "/search", params = "t")
    public ResponseEntity<List<Book>> getBooksByTitle(@RequestParam String t) {
        return new ResponseEntity<>(bookService.findBooksByTitle(t), HttpStatus.OK);
    }

    @GetMapping(value = "/search", params = "a")
    public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam String a) {
        return new ResponseEntity<>(bookService.findBooksByAuthor(a), HttpStatus.OK);
    }

    @GetMapping(value = "/search", params = "bg")
    public ResponseEntity<List<Book>> getBooksByBookGenre(@RequestParam String bg) {
        return new ResponseEntity<>(bookService.findBooksByBookGenre(bg), HttpStatus.OK);
    }

    @GetMapping(value = "/search", params = {"ls", "hs"})
    public ResponseEntity<List<Book>> getBooksByScore(@RequestParam Double ls, @RequestParam Double hs) {
        return new ResponseEntity<>(bookService.findBooksByScore(ls, hs), HttpStatus.OK);
    }

    @GetMapping(value = "/search", params = {"lp", "hp"})
    public ResponseEntity<List<Book>> getBooksByPrice(@RequestParam Double lp, @RequestParam Double hp) {
        return new ResponseEntity<>(bookService.findBooksByPrice(lp, hp), HttpStatus.OK);
    }

//    @GetMapping(value="/search",params = {"title", "author", "bookGenre", "lowScore", "highScore", "lowPrice", "highPrice"})
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
