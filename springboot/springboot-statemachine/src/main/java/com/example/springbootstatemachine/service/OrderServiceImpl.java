package com.example.springbootstatemachine.service;

import com.example.springbootstatemachine.dto.Order;
import com.example.springbootstatemachine.enums.OrderStatus;
import com.example.springbootstatemachine.enums.OrderStatusChangeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
 
    @Autowired
    private StateMachine<OrderStatus, OrderStatusChangeEvent> orderStateMachine;
 
    @Autowired
    private StateMachinePersister<OrderStatus, OrderStatusChangeEvent, Order> persister;
 
    private int id = 1;
    private Map<Integer, Order> orders = new HashMap<>();

    /**
     * @data: 2021/2/6-下午2:07
     * @User: zhaozhiwei
     * @method: create

     * @return: com.example.springbootstatemachine.dto.Order
     * @Description: 描述
     * curl -X POST -H "Content-Type=application/json;charset=utf-8" http://127.0.0.1:8080/order/create
     */
    @Override
    public Order create() {
        Order order = new Order();
        order.setStatus(OrderStatus.WAIT_PAYMENT);
        order.setId(id++);
        orders.put(order.getId(), order);
        return order;
    }
 
    @Override
    public Order pay(int id) {
        Order order = orders.get(id);
        System.out.println("threadName=" + Thread.currentThread().getName() + " 尝试支付 id=" + id);
        Message message = MessageBuilder.withPayload(OrderStatusChangeEvent.PAYED).setHeader("order", order).build();
        if (!sendEvent(message, order)) {
            System.out.println("threadName=" + Thread.currentThread().getName() + " 支付失败, 状态异常 id=" + id);
        }
        return orders.get(id);
    }
 
    @Override
    public Order deliver(int id) {
        Order order = orders.get(id);
        System.out.println("threadName=" + Thread.currentThread().getName() + " 尝试发货 id=" + id);
        if (!sendEvent(MessageBuilder.withPayload(OrderStatusChangeEvent.DELIVERY).setHeader("order", order).build(), orders.get(id))) {
            System.out.println("threadName=" + Thread.currentThread().getName() + " 发货失败，状态异常 id=" + id);
        }
        return orders.get(id);
    }
 
    @Override
    public Order receive(int id) {
        Order order = orders.get(id);
        System.out.println("threadName=" + Thread.currentThread().getName() + " 尝试收货 id=" + id);
        if (!sendEvent(MessageBuilder.withPayload(OrderStatusChangeEvent.RECEIVED).setHeader("order", order).build(), orders.get(id))) {
            System.out.println("threadName=" + Thread.currentThread().getName() + " 收货失败，状态异常 id=" + id);
        }
        return orders.get(id);
    }
 
    @Override
    public Map<Integer, Order> getOrders() {
        return orders;
    }
 
 
    /**
     * 发送订单状态转换事件
     *
     * @param message
     * @param order
     * @return
     */
    private synchronized boolean sendEvent(Message<OrderStatusChangeEvent> message, Order order) {
        boolean result = false;
        try {
            orderStateMachine.start();
            //尝试恢复状态机状态
            persister.restore(orderStateMachine, order);
            //添加延迟用于线程安全测试
            Thread.sleep(1000);
            result = orderStateMachine.sendEvent(message);
            //持久化状态机状态
            persister.persist(orderStateMachine, order);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            orderStateMachine.stop();
        }
        return result;
    }
}