package com.lx.demo.cxf;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * webservice需要先定义接口
 */
@WebService(name = "sayhello") //sei 和 sei实现类
public interface ISayHello {

    @WebMethod //sei中的方法
    String sayHello(String name);
}
