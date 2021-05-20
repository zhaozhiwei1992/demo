package com.example.springbootperformanalysis.web.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootperformanalysis.web.resource
 * @Description: TODO
 * @date 2021/5/12 下午8:21
 */
@RestController
public class MemResource {

    /**
     * @data: 2021/5/19-下午2:39
     * @User: zhaozhiwei
     * @method: mem

     * @return: java.lang.Integer
     * @Description: 描述
     * curl -X GET http://127.0.0.1:8080/mem
     * java.lang.OutOfMemoryError: Java heap space
     */
    @GetMapping("/mem")
    public Integer mem(){
        // 创建byte字节数组, 一个对象1m, 创建10000个
        final List<byte[]> integers = new ArrayList<>();
        for (int i = 0; i < 2000; i++) {
            integers.add(new byte[1024*1024]);
        }
        return integers.size();
    }
}
