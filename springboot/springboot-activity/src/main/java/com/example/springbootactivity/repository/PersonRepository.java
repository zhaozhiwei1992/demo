package com.example.springbootactivity.repository;

import com.example.springbootactivity.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhaoz
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByName(String name);
}
