package com.lx.demo;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Protocol;
import com.alibaba.dubbo.rpc.RpcContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Unit test for simple App.
 */
public class OrderServiceTest
{
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"client/order-consumer.xml"});
        context.start();
        //访问到服务端提供的实现
        IOrderService iOrderService = (IOrderService) context.getBean("orderServiceLocal");
        DoOrderRequest doOrderRequest = new DoOrderRequest();
        doOrderRequest.setName("zhao");
        DoOrderResponse doOrderResponse = iOrderService.doOrder(doOrderRequest);

        //异步处理时需要等待
//        Future<Object> future = RpcContext.getContext().getFuture();
        //DoOrderResponse(data=hello boy, code=666, memo=处理成功)
//        System.out.println(future.get());

        System.out.println("客户端 user输出: ==== " + doOrderResponse);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Protocol myprotocol = ExtensionLoader.getExtensionLoader(Protocol.class).getExtension("myprotocol");
//        System.out.println(myprotocol.getDefaultPort());

        //源码阅读入口
        Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();
        System.out.println(protocol.getDefaultPort());
    }
}
