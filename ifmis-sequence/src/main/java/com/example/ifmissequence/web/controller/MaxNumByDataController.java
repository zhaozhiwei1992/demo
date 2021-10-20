package com.example.ifmissequence.web.controller;

import com.example.ifmissequence.repository.PayRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.ifmissequence.web.controller
 * @Description: 直接根据需求, 查询最大号码,
 * 不需要加锁, 保存时校验数据库唯一约束
 * @date 2021/10/20 上午10:14
 */
@RestController
@RequestMapping("/maxnum")
public class MaxNumByDataController {

    @Autowired
    private PayRequestRepository payRequestRepository;

    @GetMapping("data")
    public String maxNumber(){
        final int maxNum = payRequestRepository.getMaxNum("30101");
        payRequestRepository.updateMaxNum("30101", maxNum);
        return String.valueOf(maxNum);
    }
}
