package com.library.Library.service;

import com.library.Library.entity.Book;
import com.library.Library.exception.BookNotFoundException;
import com.library.Library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book addBook(Book book){
        book.setBookId(UUID.randomUUID().toString());
        return bookRepository.save(book);
    }

    public List<Book> findAllBooks(){
        return bookRepository.findAll();
    }

    public Book updateBook(Book book){
        return bookRepository.save(book);
    }

    public Book findBookById(Long id){
        return bookRepository.findBookById(id)
                .orElseThrow(() -> new BookNotFoundException("Book by id = " + id + " was not found"));
    }

    public void deleteBook(Long id){
        bookRepository.deleteBookById(id);
    }
}
