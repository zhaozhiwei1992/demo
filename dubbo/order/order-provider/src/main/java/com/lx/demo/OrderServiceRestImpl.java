package com.lx.demo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo
 * @Description: TODO
 * @date 2021/9/22 下午8:47
 */
@Path("demo")
public class OrderServiceRestImpl implements IOrderService{

    @Override
    public DoOrderResponse doOrder(DoOrderRequest request) {
        return null;
    }

    @Override
    @Path("echo")
    @GET
    public String echo(){
        return "hello rest";
    }
}
