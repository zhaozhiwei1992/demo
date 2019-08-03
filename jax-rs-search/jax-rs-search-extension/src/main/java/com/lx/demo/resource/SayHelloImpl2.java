package com.lx.demo.resource;

import javax.ws.rs.Path;

/**
 * 基于cxf webservice-restful
 *　
 * 默认, 通过cxf.path可以修改命名空间
 * Mapping servlet: 'CXFServlet' to [/services/*]
 * org.apache.cxf.endpoint.ServerImpl       : Setting the server's publish address to be /ws
 *
 * curl -X GET http://127.0.0.1:8080/services/ws/sayHello2/zhangsan
 */
@Path("/sayHello2")
public class SayHelloImpl2 implements ISayHello{

    /**
     * curl -X GET http://127.0.0.1:8080/services/ws/sayHello2/zhangsan
     * @return
     */
    public String sayHello(String name){
        String msg = "springboot cxf-restful --> sayHello2 " + name;
        System.out.println(msg);
        return  msg;
    }

}
