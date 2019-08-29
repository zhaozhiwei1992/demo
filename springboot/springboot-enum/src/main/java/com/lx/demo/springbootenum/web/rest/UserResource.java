package com.lx.demo.springbootenum.web.rest;

import com.lx.demo.springbootenum.contrant.Gender;
import com.lx.demo.springbootenum.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserResource {

    /**
     * curl -X POST -H Content-Type:application/json;charset=utf-8 -d {"id":1, "name":"lisi", "age":18, "sex":"MAN"}
     * http://localhost:8080/users/
     * @param user
     * @return
     */
    @PostMapping("/users")
    public User save(@RequestBody User user){
        log.info("{}", user);
        return user;
    }

    /**
     * 这种情况不需要转换
     * curl -X POST -F gender=MALE -F type=1 http://127.0.0.1:8080/enum
     *
     * 这种需要转, 实现com.lx.demo.springbootenum.web.convert.StringToGenderConverter
     * curl -X POST -F gender=1 -F type=1 http://127.0.0.1:8080/enum
     * {"timestamp":"2019-08-29T11:10:59.699+0000","status":400,"error":"Bad Request",
     * "message":"Failed to convert value of type 'java.lang.String'
     * to required type 'com.lx.demo.springbootenum.contrant.SexEnum';
     * nested exception is org.springframework.core.convert.ConversionFailedException:
     * Failed to convert from type [java.lang.String] to type [com.lx.demo.springbootenum.contrant.SexEnum]
     * for value '1'; nested exception is java.lang.IllegalArgumentException:
     * No enum constant com.lx.demo.springbootenum.contrant.SexEnum.1","path":"/enum"}%
     * @param gender
     */
    @PostMapping("/enum")
    public void saveEnum(Gender gender){
        log.info("{}", gender);
    }
}
