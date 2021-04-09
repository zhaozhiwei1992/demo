package com.lx.demo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo
 * @Description: 未屏蔽主线程， await后面的方法等计数器为0后才开始执行
 * 线程执行 await() 方法之后计数器会减 1，并进行等待，直到计数器为 0，所有调用 await() 方法而在等待的线程才能继续执行。
 * @date 2021/3/19 上午11:47
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        int threadTotal = 10;
        final ExecutorService executorService = Executors.newCachedThreadPool();
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(threadTotal);
        System.out.println("线程开始执行..");
        for (int i = 0; i <threadTotal; i++) {
           executorService.execute(() -> {
               try {
                   System.out.println("睡1s");
                   // 这里不睡觉，保证不了顺序执行
                   Thread.sleep(1000);
                   // 控制减1
                   cyclicBarrier.await();
               } catch (InterruptedException | BrokenBarrierException e) {
                   e.printStackTrace();
               }
//               System.out.println("线程执行结束..");
           });
        }
//        cyclicBarrier.await();
        System.out.println("主线程执行结束..");
        executorService.shutdown();
    }
}
