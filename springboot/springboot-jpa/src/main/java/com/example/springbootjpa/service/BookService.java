package com.example.springbootjpa.service;

import com.example.springbootjpa.domain.Book;
import com.example.springbootjpa.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class BookService {

    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public Book save(Book book){
        Book save = bookRepository.save(book);
//        int i = 1/0; //test transactional
        return save;
    }

    //设置只读事务后是写入不了数据的， 可以在上面save方法测试
    @Transactional(readOnly = true)
    public Collection<Book> findAll(){
        return bookRepository.findAll();
    }
}
