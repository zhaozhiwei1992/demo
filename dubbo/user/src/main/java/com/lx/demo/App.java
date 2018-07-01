package com.lx.demo;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.common.utils.Assert;
import com.alibaba.dubbo.rpc.Protocol;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"META-INF/spring/order-consumer.xml"});
        context.start();
        //访问到服务端提供的实现
        IOrderService iOrderService = (IOrderService) context.getBean("orderService");
        DoOrderRequest doOrderRequest = new DoOrderRequest();
        doOrderRequest.setName("zhao");
        DoOrderResponse doOrderResponse = iOrderService.doOrder(doOrderRequest);
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
