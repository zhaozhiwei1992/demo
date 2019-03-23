package com.lx.demo.springbootjmx.jdk.standard;

/**
 * 标准实现方式管理bean后面必须是 MBean结尾
 */
public interface HelloMBean {
    String getMsg();

    void setMsg(String msg);

    String sayHello();
}
