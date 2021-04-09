package com.lx.demo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo
 * @Description: 如果多个线程对同一个共享数据进行访问而不采取同步操作的话，那么操作的结果是不一致的。
 * 测试多线程环境下， 1000个数字累加, 并发场景下，可能不是1000
 * @date 2021/4/9 下午1:48
 */
public class ThreadUnsafeDemo {

    private int count = 0;

    public int get(){
        return count;
    }

    public void add(){
        count++;
    }

    public static void main(String[] args) throws InterruptedException {
        int threadCount = 1000;
        final ThreadUnsafeDemo threadUnsafeDemo = new ThreadUnsafeDemo();
        final ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() ->{
                threadUnsafeDemo.add();

//                如果不睡觉，多线程场景下, 结果可能不是1000
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        System.out.println("累加结果: " + threadUnsafeDemo.get());
    }
}
