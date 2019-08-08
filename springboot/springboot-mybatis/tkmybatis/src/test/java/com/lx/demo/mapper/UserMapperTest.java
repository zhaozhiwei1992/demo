package com.lx.demo.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void deleteByPrimaryKey() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void insert() {
    }

    @Test
    public void insertSelective() {
    }

    @Test
    public void existsWithPrimaryKey() {
        log.info("用户是否存在: {}", userMapper.existsWithPrimaryKey(1));
    }

    @Test
    public void selectAll() {
        userMapper.selectAll().forEach(user -> log.info("用户信息: {}", user));
    }

    @Test
    public void selectByPrimaryKey() {
    }

    @Test
    public void selectCount() {
    }

    @Test
    public void select() {
    }

    @Test
    public void selectOne() {
    }

    @Test
    public void updateByPrimaryKey() {
    }

    @Test
    public void updateByPrimaryKeySelective() {
    }

    @Test
    public void deleteByExample() {
    }

    @Test
    public void selectByExample() {
    }

    @Test
    public void selectCountByExample() {
    }

    @Test
    public void selectOneByExample() {
    }

    @Test
    public void updateByExample() {
    }

    @Test
    public void updateByExampleSelective() {
    }

    @Test
    public void selectByExampleAndRowBounds() {
    }

    @Test
    public void selectByRowBounds() {
    }
}