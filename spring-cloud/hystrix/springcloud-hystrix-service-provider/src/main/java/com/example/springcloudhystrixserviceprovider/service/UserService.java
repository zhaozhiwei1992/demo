package com.example.springcloudhystrixserviceprovider.service;

import com.example.springcloudhystrixserviceprovider.domain.User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    public List<User> findAll(){
        return Arrays.asList(new User(1L, "张三", 18), new User(2L, "李四", 19));
    }
}
