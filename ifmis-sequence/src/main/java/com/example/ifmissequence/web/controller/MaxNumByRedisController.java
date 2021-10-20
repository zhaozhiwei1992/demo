package com.example.ifmissequence.web.controller;

import com.example.ifmissequence.repository.MaxNumRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.ifmissequence.web.controller
 * @Description: 通过redis获取最大号码
 * @date 2021/10/20 上午10:57
 */
@RestController
@RequestMapping("maxnum")
public class MaxNumByRedisController {

    @Autowired
    private MaxNumRedisRepository maxNumRedisRepository;

    @GetMapping("redis")
    public int maxNum(){
        final int maxNum = maxNumRedisRepository.getMaxNum("30101");
        maxNumRedisRepository.getMaxNum("30102");
        maxNumRedisRepository.getMaxNum("30103");
        System.out.println(maxNum);
        return maxNum;
    }
}
