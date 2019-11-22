package com.lx.demo.springbootscheduler.controller;

import com.lx.demo.springbootscheduler.spring.TimeTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 销毁timetask
 * @auther lx7ly
 * @create 2019-11-20 下午2:22
 */
@RestController
public class TimeTaskDestroyController {

    @Autowired
    private TimeTask timeTask;

    @GetMapping("/destroy")
    public String destroy(){
        timeTask.destroy();
        return "";
    }
}
