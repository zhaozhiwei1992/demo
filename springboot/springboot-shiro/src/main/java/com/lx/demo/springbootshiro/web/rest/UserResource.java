package com.lx.demo.springbootshiro.web.rest;

import com.lx.demo.springbootshiro.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
public class UserResource {

    /**
     * 获取所有用户
     * @return
     */
    @GetMapping("/users")
    public List<User> findAll(){
        return Arrays.asList(new User(), new User());
    }
}
