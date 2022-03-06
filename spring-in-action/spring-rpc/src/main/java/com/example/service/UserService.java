package com.example.service;

import com.example.domain.User;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.service
 * @Description: TODO
 * @date 2022/3/6 下午4:01
 */

@WebService(serviceName="UserService"
        , targetNamespace="http://corp.com/"
        , name="UserServiceSoap"
        , portName="UserServiceSoap")
public interface UserService {

    @WebMethod
    List<User> findUsers(int startIndex, int count);

    @WebMethod
    User findOne(int id);

    @WebMethod
    User save(User user);
}
