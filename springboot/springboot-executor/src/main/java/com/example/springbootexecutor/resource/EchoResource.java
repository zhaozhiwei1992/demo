package com.example.springbootexecutor.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executor;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootexecutor.resource
 * @Description: TODO
 * @date 2021/4/19 上午10:56
 */
@RestController
public class EchoResource {

    @Autowired
    @Qualifier("logExecutor")
    Executor executor;

    @RequestMapping("/echo/{msg}")
    public String echo(@PathVariable String msg){
        String result = "echo: " + msg;
        System.out.println(result);
        return result;
    }

    /**
     * @data: 2021/4/19-上午11:09
     * @User: zhaozhiwei
     * @method: ExecutorEcho
      * @param msg :
     * @return: void
     * @Description:  curl -X GET http://127.0.0.1:8080/echo/executor/11
     */
    @RequestMapping("/echo/executor/{msg}")
    public void ExecutorEcho(@PathVariable String msg){
        executor.execute(() -> {
            System.out.println(Thread.currentThread().getName() + " echo: " + msg);
        });
    }
}
