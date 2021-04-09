package com.lx.demo;

import java.math.BigInteger;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.stream.Stream;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo
 * @Description: TODO
 * @date 2021/3/21 上午11:15
 */
public class FutureTaskDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 在支线流程中处理，最后返回结果
        final FutureTask<Integer> integerFutureTask = new FutureTask<>(() -> {
            int result = 0;
            for (int i = 0; i < 10; i++) {
                Thread.sleep(10);
                result+=i;
            }
            return result;
        });

        final Thread thread = new Thread(integerFutureTask);
        thread.start();

        new Thread(()->{
            System.out.println("other task is running...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // 主流程走完找integerFutureTask获取结果
        System.out.println(integerFutureTask.get());
    }
}
