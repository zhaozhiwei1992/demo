package com.example.event;

import com.example.service.PolardbConnTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

public class PolardbConnTestEvent implements CommandLineRunner {

    @Autowired
    private PolardbConnTestService polardbConnTestService;

    @Override
    public void run(String... args) throws Exception {
        // 获取请求参数
        System.out.println("传入参数: " + args);
        int nThreads = 10;
        for (String arg : args) {
            if(arg.contains("nThreads")){
                nThreads = Integer.parseInt(arg.split("=")[1]);
            }
        }
        polardbConnTestService.exec(nThreads);
    }
}
