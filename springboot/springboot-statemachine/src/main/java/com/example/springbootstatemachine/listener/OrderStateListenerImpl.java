package com.example.springbootstatemachine.listener;

import com.example.springbootstatemachine.dto.Order;
import com.example.springbootstatemachine.enums.OrderStatus;
import com.example.springbootstatemachine.enums.OrderStatusChangeEvent;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

@Component("orderStateListener")
    @WithStateMachine(name = "orderStateMachine")
    public class OrderStateListenerImpl{
     
        @OnTransition(source = "WAIT_PAYMENT", target = "WAIT_DELIVER")
        public boolean payTransition(Message<OrderStatusChangeEvent> message) {
            Order order = (Order) message.getHeaders().get("order");
            order.setStatus(OrderStatus.WAIT_DELIVER);
            System.out.println("支付 headers=" + message.getHeaders().toString());
            return true;
        }
     
        @OnTransition(source = "WAIT_DELIVER", target = "WAIT_RECEIVE")
        public boolean deliverTransition(Message<OrderStatusChangeEvent> message) {
            Order order = (Order) message.getHeaders().get("order");
            order.setStatus(OrderStatus.WAIT_RECEIVE);
            System.out.println("发货 headers=" + message.getHeaders().toString());
            return true;
        }
     
        @OnTransition(source = "WAIT_RECEIVE", target = "FINISH")
        public boolean receiveTransition(Message<OrderStatusChangeEvent> message){
            Order order = (Order) message.getHeaders().get("order");
            order.setStatus(OrderStatus.FINISH);
            System.out.println("收货 headers=" + message.getHeaders().toString());
            return true;
        }
    }