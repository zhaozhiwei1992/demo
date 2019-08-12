package com.lx.demo.springbootmongodbmulti.repository;

import com.lx.demo.springbootmongodbmulti.domain.User;
import com.lx.demo.springbootmongodbmulti.repository.primary.UserRepositoryPrimary;
import com.lx.demo.springbootmongodbmulti.repository.secondary.UserRepositorySecondary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryMutiTest {

    @Autowired
    private UserRepositoryPrimary userRepositoryPrimary;

    @Autowired
    private UserRepositorySecondary userRepositorySecondary;

    @Test
    public void deleteByPrimaryKey() {
    }

    /**
     *
     * db1
     * { "_id" : NumberLong(98), "name" : "ttang", "age" : 18, "_class" : "com.lx.demo.springbootmongodbmulti.domain.User" }
     *
     * db2
     * { "_id" : NumberLong(100), "name" : "ww", "age" : 19, "_class" : "com.lx.demo.springbootmongodbmulti.domain.User" }
     */
    @Test
    public void insert() {
        final User user = new User();
        user.setId(98L);
        user.setName("ttang");
        user.setAge(18);

        userRepositoryPrimary.insert(user);

        final User user2 = new User();
        user2.setId(100L);
        user2.setName("ww");
        user2.setAge(19);
        userRepositorySecondary.insert(user2);
    }

    @Test
    public void selectByName() {
    }

    @Test
    public void selectByPrimaryKey() {
    }

    @Test
    public void updateByPrimaryKey() {
    }
}