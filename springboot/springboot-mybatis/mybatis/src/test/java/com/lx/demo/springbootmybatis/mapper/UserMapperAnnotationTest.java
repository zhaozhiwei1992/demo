package com.lx.demo.springbootmybatis.mapper;
import java.util.Date;
import com.lx.demo.springbootmybatis.contrant.SexEnum;

import com.lx.demo.springbootmybatis.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserMapperAnnotationTest {

    @Autowired
    private UserMapperAnnotation userMapperAnnotation;

    @Test
    public void selectByID() {
        log.info("用户信息: {}", userMapperAnnotation.selectByID(1));
    }

    @Test
    public void getAll() {
        final List<User> all = userMapperAnnotation.getAll();
        all.forEach(user -> log.info("用户信息: {}", user));
    }

    @Test
    public void getOne() {
        log.info("用户信息: {}", userMapperAnnotation.getOne(1));
    }

    @Test
    public void insert() {
        final User user = new User();
        user.setId(99);
        user.setName("ligoudancc");
        user.setPassword("996");
        user.setRealname("dangoulicc");
        user.setAvatar("xx");
        user.setMobile("110");
        user.setSex(SexEnum.MAN);
        user.setStatus(0);
        user.setCreateTime(new Date());
        userMapperAnnotation.insert(user);
    }

    @Test
    public void update() {
        final User user = new User();
        user.setId(1);
        user.setName("zhenjiba");
        user.setPassword("996");
        user.setRealname("dangoulicc");
        user.setAvatar("xx");
        user.setMobile("110");
        user.setSex(SexEnum.MAN);
        user.setStatus(0);
        user.setCreateTime(new Date());
        userMapperAnnotation.update(user);
    }

    @Test
    public void delete() {
        userMapperAnnotation.delete(18);
    }
}