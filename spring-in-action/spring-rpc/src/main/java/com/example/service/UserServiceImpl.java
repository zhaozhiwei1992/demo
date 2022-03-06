package com.example.service;

import com.example.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.service
 * @Description: TODO
 * @date 2022/3/4 下午5:20
 */
@Component
//@WebService(serviceName = "UserService")
@WebService(endpointInterface="com.example.service.UserService"
        , serviceName="UserService"
        , targetNamespace="http://soa.example.com/service"
        , name="UserServiceSoap"
        , portName="UserServiceSoap")
public class UserServiceImpl implements UserService{

    @WebMethod
    public List<User> findUsers(int startIndex, int count){
        return null;
    }

    @WebMethod
    public User findOne(int id){
        System.out.println("请求到方法 findOne");
        return new User(1, "zhangsan");
    }

    @WebMethod
    public User save(User user){
        return null;
    }
}
