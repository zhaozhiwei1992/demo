package com.example.springbootjpa.controller;

import com.example.springbootjpa.domain.Book;
import com.example.springbootjpa.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * curl -H "Content-Type:application/json;charset=UTF-8" -X POST -d '{"name":"chinese", "price":6.6}' http://127.0.0.1:8080/book/save
     * @param book
     * @return
     */
    @PostMapping(value = "/book/save")
    public Book save(@RequestBody Book book){
        return bookService.save(book);
    }

    @GetMapping(value = "/book/findall")
    public Collection<Book> findAll(){
        return bookService.findAll();
    }
}
