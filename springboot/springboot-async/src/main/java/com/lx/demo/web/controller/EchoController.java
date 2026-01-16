package com.lx.demo.web.controller;

import com.lx.demo.service.AsyncEchoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoController {

    @Autowired
    private AsyncEchoService asyncEchoService;

    /**
     * 浏览器访问: https://dev.com:8443/echo
     * curl -k "https://localhost:8443/echo?name=world"
     * :-k参数用来忽略证书， curl命令不支持.jks证书，需要转换为pem
     *
     * @param name
     * @return
     */
    @GetMapping("/echo")
    public String echo(@RequestParam(value = "name", defaultValue = "hello world") String name) {
        System.out.println("before async echo");
        asyncEchoService.asyncEcho();
        System.out.println("after async echo");
        // 异步执行方法
        return "echo" + name;
    }

}
