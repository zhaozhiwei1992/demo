package com.example.springbootresttemplate.web.controller;

import com.example.springbootresttemplate.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 通过resttemplate访问该接口
 */
@RestController
@Slf4j
public class EchoController {

    @GetMapping("/echo/{name}")
    public String echo(@PathVariable String name){
        return "echo: "  + name;
    }

    /**
     * 2019-12-20 22:23:59.845  INFO 9667 --- [nio-8080-exec-8] c.e.s.web.controller.EchoController
     * : id = 1, user = User(id=1, name=zhangsan, age=18)
     * @param user
     * @param id
     * @return
     */
    @PostMapping("/echo/userid")
    public User echo(User user, int id){
        log.info("id = {}, user = {}", id, user);
        return user;
    }

    /**
     * 2019-12-20 22:38:15.040  INFO 9667 --- [nio-8080-exec-6] c.e.s.web.controller.EchoController      : name =
     * [zhangsan], user = User(id=null, name=lisi, age=18)
     * @param user
     * @param name
     * @return
     */
    @PostMapping("/echo/username")
    public User echo(@RequestBody User user, @RequestParam String name){
        log.info("name = {}, user = {}", name, user);
        return user;
    }
}
