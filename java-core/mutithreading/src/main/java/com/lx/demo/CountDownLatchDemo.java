package com.lx.demo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo
 * @Description:
 * 用来控制一个或者多个线程等待多个线程。
 *
 * 维护了一个计数器 cnt，每次调用 countDown() 方法会让计数器的值减 1，减到 0 的时候，那些因为调用 await() 方法而在等待的线程就会被唤醒。
 *
 * @date 2021/3/18 下午8:48
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        int totalThread = 10;
        final CountDownLatch countDownLatch = new CountDownLatch(totalThread);
        final ExecutorService executorService = Executors.newCachedThreadPool();
        System.out.println("线程开始执行..");
        for (int i = 0; i < totalThread; i++) {
            executorService.execute(() -> {
                try {
                    System.out.println("睡1s");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }

//        如果没有这个，可能主线程会先执行
        countDownLatch.await();

        System.out.println("线程执行结束..");
        executorService.shutdown();
    }
}
