package com.lx.demo.springbootfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description 大佬写点东西吧
 * @auther lx7ly
 * @create 2019-11-06 下午2:26
 */
@FeignClient(name = "HelloClientFeign", url = "http://localhost:8080/server/")
public interface HelloClientFeign {

    /**
     * 当时现场有写 xx?name={name}, 这种肯定是不能通过pathvariable传入参数的
     * @param name
     * @return
     */
    @GetMapping("/hello/{name}")
    String hello(@PathVariable String name);
}
