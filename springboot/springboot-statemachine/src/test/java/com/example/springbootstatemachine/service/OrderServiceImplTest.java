package com.example.springbootstatemachine.service;

import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: JUnit3 Test Class.java.java
 * @Package com.example.springbootstatemachine.service
 * @Description: TODO
 * @date 2021/2/5 下午8:18
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest extends TestCase {

    @Autowired
    private OrderService orderService;

    public void testMultThread(){
        orderService.create();
        orderService.create();

        orderService.pay(1);

        new Thread(()->{
            orderService.deliver(1);
            orderService.receive(1);
        }).start();

        orderService.pay(2);
        orderService.deliver(2);
        orderService.receive(2);

        System.out.println(orderService.getOrders());
    }

    @Test
    public void testCreate() {
        orderService.create();
    }

    @Test
    public void testPay() {
        orderService.pay(1);
    }

    @Test
    public void testDeliver() {
        orderService.deliver(1);
    }

    @Test
    public void testReceive() {
        orderService.receive(1);
    }

    @Test
    public void testGetOrders() {
        System.out.println(orderService.getOrders());
    }
}