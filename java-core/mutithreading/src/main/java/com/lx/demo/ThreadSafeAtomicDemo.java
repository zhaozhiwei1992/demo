package com.lx.demo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo
 * @Description:
 * 如果多个线程对同一个共享数据进行访问而不采取同步操作的话，那么操作的结果是不一致的。
 * 测试多线程环境下， 1000个数字累加, 并发场景下，可能不是1000
 * 通过原子操作，实现多线程场景下数字累加
 * @date 2021/4/9 下午1:48
 */
public class ThreadSafeAtomicDemo {

    /**
     * @Description: AtomicInteger 能保证多个线程修改的原子性。
     * @author zhaozhiwei
     * @date 2021/4/9 下午2:02
     * @version V1.0
     */
    private AtomicInteger count = new AtomicInteger();

    public int get(){
        return count.get();
    }

    public void add(){
        count.incrementAndGet();
    }

    public static void main(String[] args) throws InterruptedException {
        int threadCount = 1000;
        final ThreadSafeAtomicDemo threadUnsafeDemo = new ThreadSafeAtomicDemo();
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
