package com.lx.demo.core;

import java.util.ArrayList;

/**
 * 内部接口，不对外暴露
 */
public class EchoServiceCore {

    public void echo(String msg){
        System.out.printf("echo %s \n", msg);
    }
}
