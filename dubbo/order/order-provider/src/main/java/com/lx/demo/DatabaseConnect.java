package com.lx.demo;

import java.util.ServiceLoader;

/**
 * 自定义spi测试
 */
public class DatabaseConnect {
    public static void main(String[] args) {
        ServiceLoader<DatabaseDriver> load = ServiceLoader.load(DatabaseDriver.class);
        for (DatabaseDriver databaseDriver : load) {
            System.out.println(databaseDriver.connect("localhost"));
        }
    }
}
