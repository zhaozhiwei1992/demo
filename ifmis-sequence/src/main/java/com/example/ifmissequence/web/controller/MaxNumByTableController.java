package com.example.ifmissequence.web.controller;

import com.example.ifmissequence.repository.MaxNumTableRespository;
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
 * @Description: 通过最大号码表获取号码
 * @date 2021/10/20 上午10:41
 */
@RestController
@RequestMapping("maxnum")
public class MaxNumByTableController {

    @Autowired
    private MaxNumTableRespository maxNumTableRespository;

    /**
     * @Description: 获取并更新最大号码, 实际最大号码应在业务最后更新
     */
    @GetMapping("table")
    public int maxNum(){
        final int maxNum = maxNumTableRespository.getMaxNum("30101");
        maxNumTableRespository.updateMaxNum("30101", maxNum);
        maxNumTableRespository.updateMaxNum("30102", maxNumTableRespository.getMaxNum("30102"));
        maxNumTableRespository.updateMaxNum("30103", maxNumTableRespository.getMaxNum("30103"));
        System.out.println(maxNumTableRespository.findAll());
        return maxNum;
    }
}
