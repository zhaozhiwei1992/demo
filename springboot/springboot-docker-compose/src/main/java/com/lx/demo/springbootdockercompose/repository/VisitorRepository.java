package com.lx.demo.springbootdockercompose.repository;

import com.lx.demo.springbootdockercompose.domain.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  VisitorRepository extends JpaRepository<Visitor, Long> {
    Visitor findByIp(String ip);
}