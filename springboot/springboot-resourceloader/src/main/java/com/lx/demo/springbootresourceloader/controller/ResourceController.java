package com.lx.demo.springbootresourceloader.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    @GetMapping("test")
    public String test(){
        // 启动main 方法 /home/lx7ly/workspace/demo/springboot/springboot-resourceloader/target/classes/myres
        // 使用jar包  file:/home/lx7ly/workspace/demo/springboot/springboot-resourceloader/target/springboot-resourceloader-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes!/myres
        final URL myres = Thread.currentThread().getContextClassLoader().getResource("myres");
        return myres.getPath();
    }
}
