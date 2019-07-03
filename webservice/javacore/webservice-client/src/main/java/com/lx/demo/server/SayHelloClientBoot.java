package com.lx.demo.server;

/**
 * 通过wsimport -keep http://localhost:8080/hello?wsdl 生成后的项目目录结构与server一致
 * 如果放在一起会冲突，需要单独部署项目 所以webservice-server 和 webservice-client要分开
 */
public class SayHelloClientBoot {
    public static void main(String[] args) {
        SayHelloImplService sayHelloImplService = new SayHelloImplService();
        SayHelloImpl port = sayHelloImplService.getSayHelloImplPort();
        String zhangsan = port.sayHello("zhangsan");
        System.out.println(zhangsan);
    }
}
