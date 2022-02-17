package com.example.domain;

import com.example.annotation.ProdQualifiter;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.domain
 * @Description: TODO
 * @date 2022/2/17 上午9:29
 */
@ProdQualifiter
public class ProdBean implements CommonBean{

    public ProdBean() {
        System.out.println("This is ProdBean");
    }
}
