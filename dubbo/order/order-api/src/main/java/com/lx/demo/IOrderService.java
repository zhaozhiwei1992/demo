package com.lx.demo;

public interface IOrderService {
    DoOrderResponse doOrder(DoOrderRequest request);

    default String echo(){
       return "" ;
    }
}
