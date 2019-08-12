package com.lx.demo.springbootmail.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleMailServiceTest {

    @Autowired
    private SimpleMailService simpleMailService;

    @Test
    public void sendMail() {
        simpleMailService.sendMail("test", "347061329@qq.com", "test");
    }
}