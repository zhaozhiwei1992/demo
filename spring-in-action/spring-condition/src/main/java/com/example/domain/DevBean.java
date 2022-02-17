package com.example.domain;

import com.example.annotation.DevQualifiter;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.domain
 * @Description: TODO
 * @date 2022/2/17 上午9:29
 */
@Profile("dev")
@Component
@DevQualifiter
public class DevBean implements CommonBean{
    public DevBean() {
        System.out.println("This is DevBean");
    }
}
