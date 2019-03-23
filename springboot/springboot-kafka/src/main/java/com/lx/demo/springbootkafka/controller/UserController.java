package com.lx.demo.springbootkafka.controller;

import com.lx.demo.springbootkafka.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    /**
     * curl -X POST http://127.0.0.1:8080/user/save -H "Content-Type:application/json;charset=utf8" -d '{"name":"zhangsan"}'
     *
     * 如果没有解析器时候会有下面错误
     * {"timestamp":"2019-01-30T06:56:00.174+0000","status":500,
     * "error":"Internal Server Error",
     * "message":"Can't convert value of class com.lx.demo.springbootkafka.domain.User
     *  to class org.apache.kafka.common.serialization.StringSerializer specified in value.serializer","path":"/user/save"}%
     * @param user
     * @return
     */
    @PostMapping("/user/save")
    public Boolean saveUser(@RequestBody User user){
        kafkaTemplate.send("saveUser", user);
        return true;
    }
}
