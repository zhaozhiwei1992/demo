package com.lx.demo.readinglist.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReaderRepository extends JpaRepository<User, Long> {
}
