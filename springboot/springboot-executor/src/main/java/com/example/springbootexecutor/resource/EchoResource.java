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

    @RequestMapping("/execute/time/out")
    public void executorTimeOut(){
        //java.util.concurrent.RejectedExecutionException: Task com.example.springbootexecutor.resource
        // .EchoResource$$Lambda$382/1873461416@24b51184 rejected from java.util.concurrent
        // .ThreadPoolExecutor@781d82d9[Running, pool size = 10, active threads = 10, queued tasks = 50, completed
        // tasks = 0]
        //	at java.util.concurrent.ThreadPoolExecutor$AbortPolicy.rejectedExecution(ThreadPoolExecutor.java:2063)
        //	~[na:1.8.0_181]
        //	at java.util.concurrent.ThreadPoolExecutor.reject(ThreadPoolExecutor.java:830) [na:1.8.0_181]
        //	at java.util.concurrent.ThreadPoolExecutor.execute(ThreadPoolExecutor.java:1379) [na:1.8.0_181]

        // 最大线程很小, 但是循环execute时候，会报上述错，超出最大线程
        for (int i = 0; i < 150; i++) {
            executor.execute(() ->{
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
