package com.lx.demo.server;

import javax.jws.WebService;

/**
 * 基于java原生实现webservice
 */
@WebService
public class SayHelloImpl implements ISayHello{
    public String sayHello(String name){
        String msg = "hello" + name;
        System.out.println(msg);
        return  msg;
    }
}
