package com.lx.demo;

import java.util.Objects;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo
 * @Description: TODO
 * @date 2021/6/24 下午4:50
 */
public class StaticFields {

    static {
        // 获取int, 和string 不触发静态块加载
        System.out.println("com.lx.demo.StaticFields loading");
    }

    public static final int INT = 129;

    public static final String STRING = "str";

    /**
     * @data: 2021/6/24-下午5:21
     * @return:
     * @Description: 如果使用到引用类型，会触发下述getBean里对象的提前初始化
     */
    public static final Integer INTEGER = new Integer(11);

    /**
     * @data: 2021/6/24-下午5:08
     * @return:
     * @Description: 如果不使用到initObj, 不会触发getBean里对象的初始化
     */
//    public static final InitObj initObj = InitObjUtil.getBean();
    public static final InitObj initObj = getBean();

    public void checkInit(){
        if(Objects.isNull(initObj)){
//            initObj = InitObjUtil.getBean();
        }
    }

    public static InitObj getBean(){
        return new InitObj();
    }

}

class InitObj{

    public InitObj() {
        System.out.println("构造方法被调用");
    }
}

