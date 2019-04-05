package com.example.springbootjpa.repository;

import com.example.springbootjpa.domain.Book;
import com.example.springbootjpa.domain.User;
import com.example.springbootjpa.domain.UserSql;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select id, name, age from User")
    List<User> findBySql();
}
