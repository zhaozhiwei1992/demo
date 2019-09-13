package com.lx.demo.pub;

import com.lx.demo.core.EchoServiceCore;

/**
 * 对外提供服务
 */
public class EchoResource {

    public void echo(){
        new EchoServiceCore().echo("hello");
    }
}
