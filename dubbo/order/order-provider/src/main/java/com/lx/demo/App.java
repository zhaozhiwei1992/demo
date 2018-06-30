package com.lx.demo;

import com.alibaba.dubbo.container.Main;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //下面方式要求配置文件必须放到"META-INF/spring/"目录下
        Main.main(args);

        //官方提供的方式
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
//                new String[] {"META-INF/spring/order-provider.xml"});
//        context.start();
//        // press any key to exit
//        try {
//            System.in.read();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
