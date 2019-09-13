package com.lx;


import com.lx.demo.pub.EchoResource;
import org.springframework.core.ResolvableType;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        // 测试j9+ module
        new EchoResource();

        //测试spring 依赖传递
        ResolvableType.forClass(App.class);

        System.out.println("Hello World!");
    }
}
