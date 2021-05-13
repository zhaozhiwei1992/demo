package com.example.springbootperformanalysis.web.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootperformanalysis.web.controller
 * @Description: TODO
 * @date 2021/5/12 下午8:02
 */
@RestController
public class CpuResource {

    /**
     * @data: 2021/5/13-上午9:24
     * @User: zhaozhiwei
     * @method: cpu

     * @return: java.lang.String
     * @Description:
     * 每次请求都会占满一个cpu, 电脑是8核, 全部占满应该是800%
     * "http-nio-8080-exec-4" #24 daemon prio=5 os_prio=0 cpu=776878.00ms elapsed=1039.25s tid=0x00007f23dcfe6000
     * nid=0x15856 runnable  [0x00007f233f6c7000]
     *    java.lang.Thread.State: RUNNABLE
     * 	at com.example.springbootperformanalysis.web.resource.CpuResource.cpu(CpuResource.java:21)
     */
    @GetMapping("/cpu")
    public String cpu() {
        System.out.println("模拟cpu顶满");
//        模拟cpu干满
        while (true) {

        }
    }
}
