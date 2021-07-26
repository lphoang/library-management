package com.library.Library.service.impl;

import com.library.Library.dto.responses.BookGenreResponse;
import com.library.Library.entity.BookGenre;
import com.library.Library.repository.BookGenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class BookGenreService {

    private final BookGenreRepository bookGenreRepository;
    public ResponseEntity<List<BookGenre>> getAllBookGenres() {
        return new ResponseEntity<>(bookGenreRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<BookGenreResponse> getBookById(String id) {
        Optional<BookGenre> bookGenre = bookGenreRepository.findBookGenreById(id);
        if(!bookGenre.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no genre found with id above");
        }
        BookGenreResponse response = new BookGenreResponse(
                bookGenre.get().getId(),
                bookGenre.get().getTitle(),
                bookGenre.get().getBookSet()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
