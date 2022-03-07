package com.example.service;

import com.example.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    private List<User> users = new ArrayList<User>();

    {
        users = Arrays.asList(
                new User(1, "zhangsan")
                ,new User(2, "lisi")
        );
    }

    public List<User> findUsers(int startIndex, int count){
        return users;
    }

    public User findOne(int id){
        System.out.println("请求到方法 findOne");
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
//                .orElse(new User(0, "null"));
                .orElse(null);
    }

    public User save(User user){
        System.out.println(user);
        return user;
    }
}
