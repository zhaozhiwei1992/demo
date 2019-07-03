package com.lx.demo;

import javax.jws.WebService;

/**
 * 基于java原生实现webservice
 */
@WebService
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    public String sayHello(String name){
        String msg = "hello" + name;
        System.out.println(msg);
        return  msg;
    }
}
