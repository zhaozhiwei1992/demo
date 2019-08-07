package com.lx.demo.springbootpandect.repository;

import com.lx.demo.springbootpandect.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 返回用户实体　最好都用optional
     * @param name
     * @return
     */
    Optional<User> findByName(String name);
}
