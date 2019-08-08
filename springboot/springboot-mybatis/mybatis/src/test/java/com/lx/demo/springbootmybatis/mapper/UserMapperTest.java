package com.lx.demo.springbootmybatis.mapper;

import com.lx.demo.springbootmybatis.domain.UserExample;
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
    public void countByExample() {
        final UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(1);
        final long l = userMapper.countByExample(userExample);
        log.info("用户总数: {}", l);
    }

    @Test
    public void deleteByExample() {
    }

    @Test
    public void deleteByPrimaryKey() {
    }

    @Test
    public void insert() {
    }

    @Test
    public void insertSelective() {
    }

    @Test
    public void selectByExample() {
        final UserExample userExample = new UserExample();
        userExample.createCriteria().andNameEqualTo("zhangsan");
        userMapper.selectByExample(userExample).forEach(user -> log.info("用户信息: {}", user));
    }

    @Test
    public void selectByPrimaryKey() {
    }

    @Test
    public void updateByExampleSelective() {
    }

    @Test
    public void updateByExample() {
    }

    @Test
    public void updateByPrimaryKeySelective() {
    }

    @Test
    public void updateByPrimaryKey() {
    }
}