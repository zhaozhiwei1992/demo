package com.lx.demo;

import java.util.concurrent.ExecutionException;

/**
 * @Title: RunnableDemo
 * @Package com/lx/demo/RunnableDemo.java
 * @Description: 实现接口会更好一些，因为：
 *
 *     Java 不支持多重继承，因此继承了 Thread 类就无法继承其它类，但是可以实现多个接口；
 *     类可能只要求可执行就行，继承整个 Thread 类开销过大。
 * @author zhaozhiwei
 * @date 2021/3/17 下午3:47
 * @version V1.0
 */
public class RunnableDemo implements Runnable {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        final RunnableDemo callableDemo = new RunnableDemo();
        final Thread thread = new Thread(callableDemo);
        thread.start();
    }

    @Override
    public void run() {
        System.out.println("I am Runnable");
    }
}
