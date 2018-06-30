package com.lx.demo;

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
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"META-INF/spring/order-consumer.xml"});
        context.start();
        //访问到服务端提供的实现
        IOrderService iOrderService = (IOrderService) context.getBean("orderService");
        DoOrderRequest doOrderRequest = new DoOrderRequest();
        doOrderRequest.setName("zhao");
        DoOrderResponse doOrderResponse = iOrderService.doOrder(doOrderRequest);
        System.out.println("客户端 user输出: ==== " + doOrderResponse);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
