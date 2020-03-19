package com.gallelloit.bookshop.controller;

import com.gallelloit.bookshop.errorhandling.BookIdMismatchException;
import com.gallelloit.bookshop.errorhandling.BookNotFoundException;
import com.gallelloit.bookshop.persistence.model.Book;
import com.gallelloit.bookshop.persistence.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    private Iterable findAll(){
        return bookRepository.findAll();
    }

    @GetMapping("/title/{bookTitle}")
    private List findByTitle(@PathVariable String bookTitle){
        return bookRepository.findByTitle(bookTitle);
    }

    @GetMapping("/{bookId}")
    private Book findById(@PathVariable Long bookId){
        return bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Book create(@RequestBody Book book){
        return bookRepository.save(book);
    }

    @DeleteMapping("/{bookId}")
    private void delete(@PathVariable Long bookId){
        bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);

        bookRepository.deleteById(bookId);
    }

    @PutMapping("/{bookId}")
    private Book updateBook(@RequestBody Book book, @PathVariable Long bookId){

        if(book.getId() != bookId){
            throw new BookIdMismatchException();
        }

        bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);

        return bookRepository.save(book);
    }

}
