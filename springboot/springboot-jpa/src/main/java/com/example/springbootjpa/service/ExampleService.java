package com.example.springbootjpa.service;

import com.example.springbootjpa.repository.ExampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootjpa.service
 * @Description: TODO
 * @date 2021/5/20 下午9:22
 */
@Service
public class ExampleService {

    @Autowired
    private ExampleRepository exampleRepository;

    public String query(){
        return exampleRepository.query();
    }
}
