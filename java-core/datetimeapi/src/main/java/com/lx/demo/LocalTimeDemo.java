package com.lx.demo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * @Description 大佬写点东西吧
 * @auther lx7ly
 * @create 2019-10-18 上午10:41
 */
public class LocalTimeDemo {
    public static void main(String[] args) {
        final LocalTime now = LocalTime.now();
        System.out.printf("当前时间 %s\n", now);
        System.out.printf("当前时间增加一小时 %s\n", now.plusHours(1));

        System.out.printf("当前时间一天中秒数 %s\n", now.toSecondOfDay());

        System.out.printf("增加duration3小时 %s", now.plus(Duration.ofHours(3)));

        System.out.println();
        System.out.println(DateTimeFormatter.ofPattern("MM").format(LocalDate.now()));
    }
}
