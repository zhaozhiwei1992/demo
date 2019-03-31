package com.example.web.rest;

import com.example.api.UserService;
import com.example.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 */
@RestController
public class UserResource{

    private UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    /**
     * 保存用户
     * @param user
     * @return
     */
    @PostMapping("/users")
    public boolean createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    /**
     * 查询所有用户
     * @return
     */
    @GetMapping("/users")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }
}
