package com.lx.demo;

/**
 * 测试synchronized使用
 * 字节码
 * 进入class目录下，然后javap -v -p SynchronizedDemo
 * {@see tmp.txt}
 */
public class SynchronizedDemo {
    public static void main(String[] args) {
        String str = "helloworld";
        echo(str);
        doEcho(str);
    }

    private static void echo(String helloworld) {
        synchronized (SynchronizedDemo.class){
            doEcho(helloworld);
        }
    }

    private synchronized static void doEcho(String helloworld) {
        System.out.println(helloworld);
    }
}
