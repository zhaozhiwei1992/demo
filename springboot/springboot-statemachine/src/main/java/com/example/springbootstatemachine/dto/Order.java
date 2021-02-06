package com.example.springbootstatemachine.dto;

import com.example.springbootstatemachine.enums.OrderStatus;
import lombok.Data;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootstatemachine.dto
 * @Description: TODO
 * @date 2021/2/5 下午2:56
 */
@Data
public class Order {

    private int id;

    private OrderStatus status;


}
