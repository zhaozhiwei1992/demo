package com.lx.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo
 * @Description:
 * 1. 锁的实现
 *
 * synchronized 是 JVM 实现的，而 ReentrantLock 是 JDK 实现的。
 *
 * 2. 性能
 *
 * 新版本 Java 对 synchronized 进行了很多优化，例如自旋锁等，synchronized 与 ReentrantLock 大致相同。
 *
 * 3. 等待可中断
 *
 * 当持有锁的线程长期不释放锁的时候，正在等待的线程可以选择放弃等待，改为处理其他事情。
 *
 * ReentrantLock 可中断，而 synchronized 不行。
 *
 * 4. 公平锁
 *
 * 公平锁是指多个线程在等待同一个锁时，必须按照申请锁的时间顺序来依次获得锁。
 *
 * synchronized 中的锁是非公平的，ReentrantLock 默认情况下也是非公平的，但是也可以是公平的。
 *
 * 5. 锁绑定多个条件
 *
 * 一个 ReentrantLock 可以同时绑定多个 Condition 对象。
 *
使用选择
除非需要使用 ReentrantLock 的高级功能，否则优先使用 synchronized。这是因为 synchronized 是 JVM 实现的一种锁机制，JVM 原生地支持它，而 ReentrantLock 不是所有的 JDK 版本都支持。并且使用 synchronized 不用担心没有释放锁而导致死锁问题，因为 JVM 会确保锁的释放。*
 * @date 2021/3/18 上午9:34
 */
public class ReentrantLockDemo {

    private Lock lock = new ReentrantLock();

    public void func(){
        lock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                System.out.print(i + " ");
            }
        }finally {
            // 确保释放锁，从而避免发生死锁。
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final ExecutorService executorService = Executors.newCachedThreadPool();
        final ReentrantLockDemo lockDemo = new ReentrantLockDemo();
        executorService.execute(() -> {
            lockDemo.func();
        });

        executorService.execute(() -> {
            lockDemo.func();
        });
    }
}
