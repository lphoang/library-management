package com.library.Library.service.impl;

import com.library.Library.dto.responses.AuthorResponse;
import com.library.Library.entity.Author;
import com.library.Library.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class AuthorService {
    private final AuthorRepository authorRepository;

    public ResponseEntity<List<Author>> getAllAuthors(){
        return new ResponseEntity<>(authorRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<AuthorResponse> getAuthorById(String id) {
        Optional<Author> author = authorRepository.findAuthorById(id);
        if(!author.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no author found with id above");
        }
        AuthorResponse authorResponse = new AuthorResponse(
                author.get().getId(),
                author.get().getFullName(),
                author.get().getBookSet()
        );
        return new ResponseEntity<>(authorResponse, HttpStatus.OK);
    }
}
