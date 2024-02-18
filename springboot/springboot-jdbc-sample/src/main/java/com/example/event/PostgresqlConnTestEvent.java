package com.example.event;

import com.example.service.PostgresqlConnTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PostgresqlConnTestEvent implements CommandLineRunner {

    @Autowired
    private PostgresqlConnTestService postgresqlConnTestService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("传入参数: " + args);
        int nThreads = 10;
        for (String arg : args) {
            if(arg.contains("nThreads")){
               nThreads = Integer.parseInt(arg.split("=")[1]);
            }
        }
        postgresqlConnTestService.exec(nThreads);
    }
}
