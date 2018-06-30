package com.lx.demo;

public class DemoMock implements IOrderService{
    @Override
    public DoOrderResponse doOrder(DoOrderRequest request) {
        DoOrderResponse doOrderResponse = new DoOrderResponse();
        doOrderResponse.setCode("404");
        doOrderResponse.setData("hello boy");
        doOrderResponse.setMemo("访问超时");
        return doOrderResponse;
    }
}
