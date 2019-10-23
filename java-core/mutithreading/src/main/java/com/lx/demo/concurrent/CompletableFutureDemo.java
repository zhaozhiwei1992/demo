package com.lx.demo.concurrent;

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

    public static void main(String[] args) {

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
