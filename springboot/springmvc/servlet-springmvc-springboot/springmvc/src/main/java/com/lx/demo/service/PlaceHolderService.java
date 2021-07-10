package com.lx.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PlaceHolderService implements PlaceHolderInterface {

    @Value("${spring.application.name}")
    private String appname;

    /**
     * 增加produces, 解决返回前端汉字乱码问题
     * @return
     */
    @Override
    public String echo(){
        return appname;
    }
}
