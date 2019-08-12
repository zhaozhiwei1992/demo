package com.lx.demo.springbootmongodb.repository;

import com.lx.demo.springbootmongodb.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void deleteByPrimaryKey() {
        userRepository.deleteByPrimaryKey(1);
    }

    /**
     * 通过客户端查看
     * use testdb
     * db.user.find()
     */
    @Test
    public void insert() {
        final User user = new User();
        user.setId(1L);
        user.setName("ttang");
        user.setAge(18);

        userRepository.insert(user);
    }

    @Test
    public void selectByName() {
        final List<User> ttang = userRepository.selectByName("ttang");
        log.info("{}, {}", Thread.currentThread().getStackTrace()[1].getMethodName(), ttang);
    }

    @Test
    public void selectByPrimaryKey() {
        final User user = userRepository.selectByPrimaryKey(1);
        log.info("{}, {}", Thread.currentThread().getStackTrace()[1].getMethodName(), user);
    }

    @Test
    public void updateByPrimaryKey() {
        final User user = new User();
        user.setId(1L);
        user.setName("ww");
        user.setAge(19);
        log.info("{}, {}", Thread.currentThread().getStackTrace()[1].getMethodName(), user);
        userRepository.updateByPrimaryKey(user);
        final List<User> ww = userRepository.selectByName("ww");
        log.info("{}, {}", Thread.currentThread().getStackTrace()[1].getMethodName(), ww);
    }
}