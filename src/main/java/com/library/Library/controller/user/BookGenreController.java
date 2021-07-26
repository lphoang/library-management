package com.library.Library.controller.user;

import com.library.Library.dto.responses.BookGenreResponse;
import com.library.Library.entity.BookGenre;
import com.library.Library.service.impl.BookGenreService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/genres")
@AllArgsConstructor
public class BookGenreController {

    private final BookGenreService bookGenreService;

    @GetMapping
    public ResponseEntity<List<BookGenre>> getAllBookGenres(){
        return bookGenreService.getAllBookGenres();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookGenreResponse> getBookGenre(@PathVariable("id") String id){
        return bookGenreService.getBookById(id);
    }
}
