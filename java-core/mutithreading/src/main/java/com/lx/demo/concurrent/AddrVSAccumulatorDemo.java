package com.lx.demo.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Description 大佬写点东西吧
 * @auther lx7ly
 * @create 2019-10-21 下午3:26
 */
public class AddrVSAccumulatorDemo {

    public static void main(String[] args) throws InterruptedException {
//        accumulator();
        addr();
    }

    private static void addr() {
        final ExecutorService thread100 = Executors.newFixedThreadPool(100);
        final LongAdder longAdder = new LongAdder();
        for (int i = 0; i < 1000; i++) {
            thread100.submit(()->{
                System.out.printf("当前线程 %s, 当前值 %s\n", Thread.currentThread().getName(), longAdder.longValue());
                longAdder.increment();
            });
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("最终值 %s\n", longAdder.longValue());
        thread100.shutdown();
    }

    private static void accumulator() throws InterruptedException {
        final LongAccumulator longAccumulator = new LongAccumulator(Long::sum, 0);
        final ExecutorService thread100 = Executors.newFixedThreadPool(100);

        //对一个０值100线程环境下累加1000次
        for (int i = 0; i < 1000; i++) {
            thread100.submit(() -> {
                System.out.printf("当前线程 %s, 当前值 %s\n", Thread.currentThread().getName(), longAccumulator.get());
                longAccumulator.accumulate(1);
            });
        }

        Thread.sleep(1000);
        System.out.printf("最终值 %s\n", longAccumulator.get());
        thread100.shutdown();
    }
}
