package com.lx.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo
 * @Description: Semaphore 类似于操作系统中的信号量，可以控制对互斥资源的访问线程数。
 *               这玩意儿可以
 *               Semaphore可以用来做流量分流，特别是对公共资源有限的场景，比如数据库连接。
 * 假设有这个的需求，读取几万个文件的数据到数据库中，由于文件读取是IO密集型任务，可以启动几十个线程并发读取，但是数据库连接数只有10个，这时就必须控制最多只有10
 * 个线程能够拿到数据库连接进行操作。这个时候，就可以使用Semaphore做流量控制。
 *
 * 作者：占小狼
 * 链接：https://www.jianshu.com/p/0090341c6b80
 * 来源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 * @date 2021/3/21 上午11:03
 */
public class SemaphoreDemo {

    public static void main(String[] args) {

        // 10个线程三个人分
        final int clientCount = 3;
        final int totalRequestCount = 10;
        Semaphore semaphore = new Semaphore(clientCount);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < totalRequestCount; i++) {
            executorService.execute(()->{
                try {
//                    表示尝试获取1个许可
                    semaphore.acquire();
                    // 1 0 1 2 2 1 2 1 1 0 (输出不一定)
                    System.out.print(semaphore.availablePermits() + " ");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            });
        }
        executorService.shutdown();
    }
}
