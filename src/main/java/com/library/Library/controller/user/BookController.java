package com.library.Library.controller.user;

import com.library.Library.dto.responses.BookResponse;
import com.library.Library.entity.Book;
import com.library.Library.service.impl.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/books")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBook(@PathVariable String id) {
        return bookService.findBookById(id);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        return bookService.findAllBooks(page, size);
    }

    @GetMapping(value = "/search", params = "t")
    public ResponseEntity<List<Book>> getBooksByTitle(@RequestParam("t") String t) {
        return new ResponseEntity<>(bookService.findBooksByTitle(t), HttpStatus.OK);
    }

    @GetMapping(value = "/search", params = "a")
    public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam("a") String a) {
        return new ResponseEntity<>(bookService.findBooksByAuthor(a), HttpStatus.OK);
    }

    @GetMapping(value = "/search", params = "bg")
    public ResponseEntity<List<Book>> getBooksByBookGenre(@RequestParam("bg") String bg) {
        return new ResponseEntity<>(bookService.findBooksByBookGenre(bg), HttpStatus.OK);
    }

    @GetMapping(value = "/search", params = {"ls", "hs"})
    public ResponseEntity<List<Book>> getBooksByScore(@RequestParam("ls") Double ls, @RequestParam("hs") Double hs) {
        return new ResponseEntity<>(bookService.findBooksByScore(ls, hs), HttpStatus.OK);
    }

    @GetMapping(value = "/search", params = {"lp", "hp"})
    public ResponseEntity<List<Book>> getBooksByPrice(@RequestParam("lp") Double lp, @RequestParam("hp") Double hp) {
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
