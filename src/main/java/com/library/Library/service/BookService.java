package com.library.Library.service;

import com.library.Library.entity.Author;
import com.library.Library.entity.Book;
import com.library.Library.entity.BookGenre;
import com.library.Library.exception.BookNotFoundException;
import com.library.Library.repository.AuthorRepository;
import com.library.Library.repository.BookGenreRepository;
import com.library.Library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookGenreRepository bookGenreRepository;

    @Autowired
    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository,
                       BookGenreRepository bookGenreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookGenreRepository = bookGenreRepository;
    }

    public Book addBook(Book book){
        boolean isBookExist = bookRepository
                .findBookByTitle(book.getTitle())
                .isPresent();
        if(isBookExist) throw new IllegalStateException("This book is already in library");

        boolean isAuthorExist = authorRepository
                .findAuthorByFullName(book.getAuthor())
                .isPresent();

        if(!isAuthorExist){
            Author author = new Author(
                    book.getAuthor()
            );
            authorRepository.save(author);
        }

        boolean isGenreExist = bookGenreRepository
                .findGenreByTitle(book.getBookGenre())
                .isPresent();

        if(!isGenreExist){
            BookGenre bookGenre = new BookGenre(
                    book.getBookGenre()
            );
            bookGenreRepository.save(bookGenre);
        }

        return bookRepository.save(book);
    }

    public List<Book> findAllBooks(){
        return bookRepository.findAll();
    }

    public Book updateBook(Book book){
        return bookRepository.save(book);
    }

    public Book findBookById(String id){
        return bookRepository.findBookById(id)
                .orElseThrow(() -> new BookNotFoundException("Book by id = " + id + " was not found"));
    }

    public void deleteBook(String id){
        bookRepository.deleteBookById(id);
    }
}
