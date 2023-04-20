package com.lx.demo.restonspringmvc.controller;

import com.lx.demo.restonspringmvc.model.Person;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class MutiParamController {

    /**
     * 没有requestbody时候传入的id, name同时会反馈到person中
     * @param person
     * @param id
     * @param name
     * @return
     */
    @PostMapping("/mutiparam/{id}")
    public String mutiParam(Person person, @PathVariable("id") int id, @RequestParam String name){
        System.out.println(id);
        System.out.println(name);
        System.out.println(person);
        return "xx";
    }

    /**
     * curl -X POST http://127.0.0.1:8080/mutiparam2?id=11&age=18&name=zhangsan&email=xx -H
     * Content-Type:application/json;charset=utf8
     * {"timestamp":"2019-09-20T08:59:05.318+0000","status":400,"error":"Bad Request","message":"Required request body is missing: public java.lang.String com.lx.demo.restonspringmvc.controller.MutiParamController.mutiParam2(com.lx.demo.restonspringmvc.model.Person)","path":"/mutiparam2"}
     * 上述方式请求会报错，　没有requestbody
     *
     * curl -X POST http://127.0.0.1:8080/mutiparam2 -H Content-Type:application/json;charset=utf8 -d {"id":1,
     * "age":18,"name":"goudan"}
     * 这种方式是可行的
     * @param person
     * @param name 这个参数得url中通过 name=xx单独传, requetbody中属性是不会解析的
     * @return
     */
    @PostMapping("/mutiparam2")
    public String mutiParam2(String name, @RequestBody Person person){
        System.out.println(name);
        System.out.println(person);
        return "";
    }

    /**
     * 不加入 requestbody
     * curl -X POST http://127.0.0.1:8080/mutiparam3 -H Content-Type:application/json;charset=utf8 -d {"id":1,"age":18}
     * 上述方式数据传入不到person中
     *
     *  curl -X POST http://127.0.0.1:8080/mutiparam3?id=11&age=18&name=zhangsan&email=xx -H
     *  Content-Type:application/json;charset=utf8
     *  这种方式可以对person对象赋值
     * @param person
     * @return
     */
    @PostMapping("/mutiparam3")
    public String mutiParam3(Person person){
        System.out.println(person);
        return "";
    }
}
