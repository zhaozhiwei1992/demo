package com.lx.demo.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description 从java5开始增加ccuncurrent相关操作，简化并发编程得难度
 * @auther lx7ly
 * @create 2019-10-20 下午4:43
 */
public class ThreadPoolDemo {

    public static void main(String[] args) {
        final long l = System.currentTimeMillis();
        final ExecutorService threads5 = Executors.newFixedThreadPool(5);

        //从5个线程池中创建十个线程操作，　这样肯定某些时候线程复用
        //结果
//        当前线程 [pool-1-thread-1]
//        当前线程 [pool-1-thread-5]
//        当前线程 [pool-1-thread-4]
//        当前线程 [pool-1-thread-3]
//        当前线程 [pool-1-thread-2]
//        当前线程 [pool-1-thread-1]
//        当前线程 [pool-1-thread-4]
//        当前线程 [pool-1-thread-5]
//        当前线程 [pool-1-thread-2]
//        当前线程 [pool-1-thread-3]
        for (int j = 0; j < 10; j++) {
            threads5.execute(() -> {
                // 输出线程信息
                System.out.printf("当前线程 [%s]\n", Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        // 主线程只管分配，分配完就玩事儿，线程自己玩自己的
        System.out.println(System.currentTimeMillis() - l);
    }
}
