package com.lx.demo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo
 * @Description: 基于BlockingQueue实现生成消费
 *     FIFO 队列 ：LinkedBlockingQueue、ArrayBlockingQueue（固定长度）
 *     优先级队列 ：PriorityBlockingQueue
 *
 * 提供了阻塞的 take() 和 put() 方法：如果队列为空 take() 将阻塞，直到队列中有内容；如果队列为满 put() 将阻塞，直到队列有空闲位置。
 * @date 2021/3/21 下午10:23
 */
public class ProducerCondumerDemo2 {

    private static BlockingQueue blockingQueue = new ArrayBlockingQueue<>(5);

    static class Producer extends Thread{

        @Override
        public void run() {
            try {
                blockingQueue.put("塞入产品");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("产品已产生");
        }
    }

    static class Consumer extends Thread{

        @Override
        public void run() {
            try {
                blockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("产品已消费");
        }
    }

    /**
     * @data: 2021/3/21-下午10:31
     * @User: zhaozhiwei
     * @method: main
      * @param args :
     * @return: void
     * @Description:
     * 产品已产生
     * 产品已产生
     * 产品已消费
     * 产品已消费
     * 产品已产生
     * 产品已产生
     * 产品已产生
     * 产品已消费
     * 产品已消费
     * 产品已消费
     * 符合为空阻塞
     */
    public static void main(String[] args) {

        for (int i = 0; i < 2; i++) {
            new Producer().start();
        }

        for (int i = 0; i < 5; i++) {
            new Consumer().start();
        }

        for (int i = 0; i < 3; i++) {
            new Producer().start();
        }
    }
}
