package com.lx.demo.cxfrestful.resource;

/**
 * 基于cxf webservice-restful
 *　
 * 默认, 通过cxf.path可以修改命名空间
 * Mapping servlet: 'CXFServlet' to [/services/*]
 * org.apache.cxf.endpoint.ServerImpl       : Setting the server's publish address to be /ws
 *
 * curl -X GET http://127.0.0.1:8080/services/ws/sayHello/zhangsan
 */
public class SayHelloImpl implements ISayHello{

    /**
     * curl -X GET http://127.0.0.1:8080/services/ws/sayHello/zhangsan
     * @return
     */
    public String sayHello(String name){
        String msg = "springboot cxf-restful --> hello" + name;
        System.out.println(msg);
        return  msg;
    }

}
