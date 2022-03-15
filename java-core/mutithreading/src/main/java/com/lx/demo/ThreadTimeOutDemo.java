package com.lx.demo;

import java.util.concurrent.*;

/**
 * @Description: 测试超时
 * @date 2022/3/15 下午3:16
 */
public class ThreadTimeOutDemo {

    public static void main(String[] args) {

        final ExecutorService exec = Executors.newFixedThreadPool(1);
        Callable<String> call = new Callable<String>() {
            public String call() throws Exception {
                //这里写可能需要超时的逻辑, 这里写2000, 下面只允许1000必定会超时得
                Thread.sleep(2000);
                return "true";
            }
        };

        try {
            Future<String> future = exec.submit(call);
            // set db connection timeout to 10 seconds
            String obj = future.get(1000, TimeUnit.MILLISECONDS);
            System.out.println("the return value from call is :" + obj);
        } catch (TimeoutException ex) {
            System.out.println("====================task time out===============");
            ex.printStackTrace();
        } catch (Exception e) {
            System.out.println("failed to handle.");
            e.printStackTrace();
        }
        // close thread pool
        exec.shutdown();

    }
}
