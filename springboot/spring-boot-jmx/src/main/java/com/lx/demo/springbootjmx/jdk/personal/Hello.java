package com.lx.demo.springbootjmx.jdk.personal;

public class Hello implements HelloMBean{
    @Override
    public String getMsg() {
        return "helloworld";
    }
}
