package com.lx.demo;

public class OrderServiceImplV2 implements IOrderService{
    @Override
    public DoOrderResponse doOrder(DoOrderRequest request) {
        System.out.println("version2.0 服务端输出, 第三方请求参数为: ========== " + request);
        DoOrderResponse doOrderResponse = new DoOrderResponse();
        doOrderResponse.setCode("666");
        doOrderResponse.setData("hello boy");
        doOrderResponse.setMemo("处理成功");
        return doOrderResponse;
    }
}
