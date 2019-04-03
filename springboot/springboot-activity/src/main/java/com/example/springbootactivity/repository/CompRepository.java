package com.example.springbootactivity.repository;

import com.example.springbootactivity.domain.Comp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhaoz
 */
@Repository
public interface CompRepository extends JpaRepository<Comp, Long> {

}
