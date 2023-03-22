package com.example.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class Order {

    /**
     * 订单原价金额
     */
    private int price;

    /**
     *下单人
     */
    private User user;

    /**
     *积分
     */
    private int score;

    /**
     * 下单日期
     */
    private Date bookingDate;
}