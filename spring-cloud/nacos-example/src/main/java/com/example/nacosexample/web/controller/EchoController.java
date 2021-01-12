package com.example.nacosexample.web.controller;

import com.example.nacosexample.config.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoController {

    @Value("${server.port}")
    private String port;

    /**
     * @data: 2021/1/12-下午3:54
     * @User: zhaozhiwei
     * @method: echo
      * @param string :
     * @return: java.lang.String
     * @Description: 通过客户端请求观察port, loadbalance 默认均衡请求
     */
    @RequestMapping(value = "/echo/{string}", method = RequestMethod.GET)
    public String echo(@PathVariable String string) {
        return "Hello Nacos Discovery " + string + " server.port: " + port;
    }

    @Autowired
    private ApplicationProperties applicationProperties;

    @RequestMapping(value = "/echo/props")
    public String echo(){
        return applicationProperties.toString();
    }
}