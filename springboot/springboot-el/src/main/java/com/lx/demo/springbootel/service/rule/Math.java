package com.lx.demo.springbootel.service.rule;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * @Title: null.java
 * @Package: com.lx.demo.springbootel.service.rule
 * @Description: TODO
 * @author: zhaozhiwei
 * @date: 2023/6/29 上午10:04
 * @version: V1.0
 */
public class Math {

    public static BigDecimal sum(BigDecimal ...param){
        return Arrays.stream(param).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static BigDecimal abs(BigDecimal value){
        return value.abs();
    }
}
