package com.lx.demo.cxf;

import org.springframework.stereotype.Component;

import javax.jws.WebService;

/**
 * 基于java原生实现webservice
 */
@Component
@WebService(endpointInterface = "com.lx.demo.cxf.ISayHello")
public class SayHelloImpl implements ISayHello{
    public String sayHello(String name){
        String msg = "springboot --> hello" + name;
        System.out.println(msg);
        return  msg;
    }
}
