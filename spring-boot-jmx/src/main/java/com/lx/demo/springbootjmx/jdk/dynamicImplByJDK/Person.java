package com.lx.demo.springbootjmx.jdk.dynamicImplByJDK;

/**
 *  问题: 为什么 jsoncole中toString是操作， get set不是
 */
public interface Person {
    long getId();

    void setId(long id);

    String getName();

    void setName(String name);

    int getAge();

    void setAge(int age);

    @Override
    String toString();
}
