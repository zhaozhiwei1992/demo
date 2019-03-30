package com.example.springcloudhystrixserviceprovider.controller;

import com.example.springcloudhystrixserviceprovider.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeoutException;

/**
 * 使用传统方式拦截异常
 * 当方法执行时间超过 100 ms 时，触发异常
 */
@RestController
public class CustomExceptionHandlerController {

    private static Random random = new Random();

    @GetMapping("")
    public List<User> getUsers() throws TimeoutException {
        long executeTime = random.nextInt(200);
        if (executeTime > 100) { // 执行时间超过了 100 ms
            throw new TimeoutException(String.format("Execute %ss timeout!", executeTime));
        }
        return Arrays.asList(new User(1L, "张三", 18), new User(2L, "李四", 19));
    }
}
