package com.example.springboothessian.controller;

import com.example.springboothessian.web.hessian.SimpleHessian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
public class SimpleHessianController {

    @Autowired
    private SimpleHessian simpleHessian;

    @GetMapping("/testSayHello/{name}")
    public String testSayHello(@PathVariable("name") String name){
//        实际使用时, 通过org.springframework.remoting.caucho.HessianProxyFactoryBean.getObject方法将代理对象返回客户端
//        从而代理请求sayhello,　spring封装其它转换逻辑
        return simpleHessian.sayHello(name);
    }
}
