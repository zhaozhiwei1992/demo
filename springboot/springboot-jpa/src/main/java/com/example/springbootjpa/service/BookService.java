package com.example.springbootjpa.service;

import com.example.springbootjpa.domain.Book;
import com.example.springbootjpa.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BookService {

    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(Book book){
        return bookRepository.save(book);
    }

    public Collection<Book> findAll(){
        return bookRepository.findAll();
    }
}
