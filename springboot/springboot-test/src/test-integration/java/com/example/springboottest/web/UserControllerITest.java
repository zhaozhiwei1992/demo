package com.example.springboottest.web;

import com.example.springboottest.configuration.UserConfiguration;
import com.example.springboottest.domain.User;
import com.example.springboottest.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: JUnit5 Test Class.java.java
 * @Package com.example.springboottest.web
 * @Description: TODO
 * @date 2021/10/20 下午3:26
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerITest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void findOne() throws Exception {

        String url = "http://127.0.0.1:8080/user";
        final ResponseEntity<User> exchange = restTemplate.exchange(
                RequestEntity.get(new URI(url)).build()
                        , User.class);

        Assert.isTrue(exchange.getStatusCode().equals(HttpStatus.OK), "获取用户接口请求异常");
    }
}