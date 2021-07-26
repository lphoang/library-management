package com.library.Library.controller.admin;

import com.library.Library.dto.requests.BookCreateRequest;
import com.library.Library.entity.Book;
import com.library.Library.service.impl.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/admin/books")
@AllArgsConstructor
public class AdminBookController {

    private final BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestBody BookCreateRequest request) {
        return bookService.addBook(request);
    }

    //TODO check again
    @PutMapping("/update/{id}")
    public ResponseEntity<Book> updateBookDetails(@RequestBody BookCreateRequest book, @PathVariable("id") String id) {
        return bookService.updateBook(book, id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable("id") String id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
