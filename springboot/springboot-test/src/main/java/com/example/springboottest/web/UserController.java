package com.example.springboottest.web;

import com.example.springboottest.domain.User;
import com.example.springboottest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springboottest.web
 * @Description: TODO
 * @date 2021/10/20 下午3:01
 */
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("user")
    public ResponseEntity<User> findOne(){
        final User user = userRepository.findOne();
        return ResponseEntity.ok().body(user);
    }
}
