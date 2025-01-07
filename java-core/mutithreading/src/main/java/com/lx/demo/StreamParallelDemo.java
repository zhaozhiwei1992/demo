package com.lx.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo
 * @Description:
 * 测试stream的并行执行
 *
 */
public class StreamParallelDemo {

    public static void main(String[] args) throws InterruptedException {
        List<Integer> integers = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
           integers.add(i);
        }
        System.out.println("线程开始执行..");
        final long l = System.currentTimeMillis();
        integers.parallelStream().forEach(i -> {
            System.out.println("睡1s, " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println("线程执行结束..");
        System.out.println(System.currentTimeMillis() - l);
    }
}
