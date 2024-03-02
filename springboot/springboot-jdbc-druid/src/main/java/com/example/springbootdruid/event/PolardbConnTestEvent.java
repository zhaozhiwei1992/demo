package com.example.springbootdruid.event;

import com.example.springbootdruid.service.PolardbConnTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class PolardbConnTestEvent implements CommandLineRunner {

    @Autowired
    private PolardbConnTestService polardbConnTestService;

    @Override
    public void run(String... args) throws Exception {
        // 获取请求参数
        System.out.println("传入参数: " + String.join(",", args));
        int nThreads = 10;
        for (String arg : args) {
            if(arg.contains("nThreads")){
                nThreads = Integer.parseInt(arg.split("=")[1]);
            }
        }

        CountDownLatch countDownLatch = new CountDownLatch(nThreads);
        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);

        // 模拟创建和释放数据库连接
        for (int j = 0; j < nThreads; j++) {
            executorService.execute(() -> {
                countDownLatch.countDown();
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                polardbConnTestService.exec();
            });
        }

        // 关闭线程池
        executorService.shutdown();
    }
}
