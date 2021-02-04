package com.example.springbootresttemplate.web.controller;

import com.example.springbootresttemplate.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    /**
     * 请求带多参数，并且大数据量
     */
    @PostMapping("/echo/mutiparam")
    public List<Map> echo(@RequestBody List<Map> datas, @RequestParam String param1, @RequestParam String param2){
        log.debug("参数列表 datas:{}, param1:{}, param2:{}", datas, param1, param2);
        return datas;
    }

    /**
     * @data: 2021/2/4-上午11:38
     * @User: zhaozhiwei
     * @method: regexEcho
     * @param id :
     * @return: java.lang.String
     * @Description: id只能传入数字，但是提示不够优雅，应该使用@Valid注解进行参数校验
     */
    @GetMapping("/echo/regex/{id:[0-9]+}")
    public String regexEcho(@PathVariable String id){
        return "传入id: "  + id;
    }
}
