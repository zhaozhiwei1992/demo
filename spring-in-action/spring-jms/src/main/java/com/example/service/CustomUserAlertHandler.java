package com.example.service;

import com.example.domain.User;
import org.springframework.stereotype.Component;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.service
 * @Description:
 * 不实现MessageListener, 自定义方法
 * @date 2022/3/29 下午6:03
 */
@Component
public class CustomUserAlertHandler {

    public void handlerUserAlert(User user){

        System.out.println("这里是自定义的AlertHandler");
        System.out.println(user);

    }
}
