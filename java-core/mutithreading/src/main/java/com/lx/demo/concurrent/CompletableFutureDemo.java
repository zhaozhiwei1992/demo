package com.lx.demo.concurrent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Description 大佬写点东西吧
 * @auther lx7ly
 * @create 2019-10-23 上午10:55
 */
public class CompletableFutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

//        test1();
        test2();
    }

    private static void test2() throws ExecutionException, InterruptedException {
        Long totalBegin  = System.currentTimeMillis();

        List<Integer> integers = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            integers.add(i);
        }
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(()->{
            try {
                integers.parallelStream().forEach(i -> {
                    System.out.println("task1_睡1s, " + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "task1";
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(()->{
            try {
                integers.parallelStream().forEach(i -> {
                    System.out.println("task2_睡1s, " + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "task2";
        });
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(()->{
            try {
                integers.parallelStream().forEach(i -> {
                    System.out.println("task3_睡1s, " + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "task3";
        });

//        Long a  = System.currentTimeMillis();
//        future1.join();
//        Long b  = System.currentTimeMillis();
//        System.out.println("feature1取值耗时:"+ (b-a) + ", 返回值: " + future1.get());
//
//        a  = System.currentTimeMillis();
//        future2.join();
//        b  = System.currentTimeMillis();
//        System.out.println("feature2取值耗时:"+ (b-a) + ", 返回值: " + future2.get());
//
//        a  = System.currentTimeMillis();
//        // get和join都会阻塞主线程
////        future3.join();
////        future3.get();
//        b  = System.currentTimeMillis();
//        System.out.println("feature3取值耗时:"+ (b-a) + ", 返回值: " + future3.get());


        CompletableFuture<Void> future = CompletableFuture.allOf(future1, future2, future3).whenComplete((result, ex) -> {
            if (ex != null) {
                System.out.println(ex.getMessage());
            }
            if (!future1.isCompletedExceptionally()) {
                Long a = System.currentTimeMillis();
                System.out.println("future1 " + future1.join());
                Long b = System.currentTimeMillis();
                //当 whenComplete 被调用时，所有的 CompletableFuture 对象（future1、future2 和 future3）已经完成了执行，因为 allOf 已经确保了这一点。
                // 在 whenComplete 中的代码块是在所有异步任务完成后执行的，因此 join() 调用不会阻塞等待任务完成，而是直接返回已经计算好的结果
                System.out.println("feature1取值耗时:" + (b - a));
            }
            if (!future2.isCompletedExceptionally()) {
                Long a = System.currentTimeMillis();
                System.out.println("future2 " + future2.join());
                Long b = System.currentTimeMillis();
                System.out.println("feature2取值耗时:" + (b - a));
            }
            if (!future3.isCompletedExceptionally()) {
                Long a = System.currentTimeMillis();
                System.out.println("job3 " + future3.join());
                Long b = System.currentTimeMillis();
                System.out.println("feature3取值耗时:" + (b - a));
            }
        });
        future.join();
        System.out.println("做一些别的操作!");
        System.out.println("总耗时:"+ (System.currentTimeMillis()-totalBegin));
    }

    private static void test1() {
        //传统future
        Future<String> page = getPage("xx");
        try {
            // get阻塞，后面全跑不了,不够优雅
            final String content = page.get();
            List<String> urls = getUrls(content);
            System.out.println(urls);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("非阻塞getpage begin---");
        final CompletableFuture<String> xx = getPageCompletable("xx");
        // 这里时异步操作得
        xx.thenApply(CompletableFutureDemo::getUrls) //这部分会去要数据
                .thenAccept(System.out::println);
        System.out.println("非阻塞getpage 我先跑---");

        // 这里延时时为了让上面completablefuture跑完，　内部操作时异步得
        xx.join();
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.exit(0);
    }

    private static List<String> getUrls(String content) {
        return Arrays.asList(content.split(","));
    }

    /**
     * future阻塞模拟
     * @param xx
     * @return
     */
    private static CompletableFuture<String> getPageCompletable(String xx) {
        String urls = "www.baidu.com, www.google.com, www.bing.com";
        return CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return urls;
        });
    }

    /**
     * future阻塞模拟
     * @param xx
     * @return
     */
    private static Future<String> getPage(String xx) {
        String urls = "www.baidu.com, www.google.com, www.bing.com";
        return Executors.newSingleThreadExecutor().submit(()-> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return urls;
        });
    }
}
