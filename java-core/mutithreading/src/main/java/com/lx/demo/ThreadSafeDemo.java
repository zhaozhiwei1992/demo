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
 * 如果多个线程对同一个共享数据进行访问而不采取同步操作的话，那么操作的结果是不一致的。
 * 测试多线程环境下， 1000个数字累加, 并发场景下，可能不是1000
 * @date 2021/4/9 下午1:48
 */
public class ThreadSafeDemo {

    private int count = 0;

    public int get(){
        return count;
    }

    /**
     * @Title: ThreadSafeDemo
     * @Package com/lx/demo/ThreadSafeDemo.java
     * @Description: 增加同步后，累加结果满足要求，正好是1000
     * @author zhaozhiwei
     * @date 2021/4/9 下午1:56
     * @version V1.0
     */
    public synchronized void add(){
        count++;
    }

    public static void main(String[] args) throws InterruptedException {
        int threadCount = 1000;
        final ThreadSafeDemo threadUnsafeDemo = new ThreadSafeDemo();
        final ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() ->{
                threadUnsafeDemo.add();

                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        System.out.println("累加结果: " + threadUnsafeDemo.get());
    }
}
