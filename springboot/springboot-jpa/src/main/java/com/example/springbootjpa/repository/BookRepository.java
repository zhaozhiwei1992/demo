package com.example.springbootjpa.repository;

import com.example.springbootjpa.domain.Book;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

/**
 * book 仓储
 */
@Repository
public class BookRepository extends SimpleJpaRepository<Book, Long> {

    public BookRepository(EntityManager em) {
        super(Book.class, em);
    }
}
