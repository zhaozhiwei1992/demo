package com.example.service;

import com.example.domain.User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    public List<User> findUsers(int startIndex, int count){
        return Arrays.asList(
                new User(1, "zhangsan")
                ,new User(2, "lisi")
        );
    }

    public User findOne(int id){
        System.out.println("请求到方法 findOne");
        return new User(id, "zhangsan");
    }

    public User save(User user){
        return null;
    }
}
