package com.lx.demo;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @Description 大佬写点东西吧
 * @auther lx7ly
 * @create 2019-10-18 下午12:39
 */
public class ZoneDateTimeDemo {
    public static void main(String[] args) {
        //确定当前时区, 只要Asia喝Ａmerica得
        ZoneId.getAvailableZoneIds().stream().filter(zone -> {
            return zone.startsWith("Asia") || zone.startsWith("America");
        }).forEach(System.out::println);

        //2019-10-18T12:57:58.449+08:00[Asia/Shanghai]
        final ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.printf("当前时间 %s\n", zonedDateTime);

        final ZonedDateTime zonedDateTime1 = ZonedDateTime.of(2019, 10, 18, 12, 57, 58, 0, ZoneId.of("America/New_York"));
        System.out.println(zonedDateTime1);

        //比较多个时区
        //转成utc时区后时间差八个小时
        final Instant instant = zonedDateTime.toInstant();
        System.out.printf("当前时间得格林威治时间 %s\n",instant.atZone(ZoneId.of("UTC")));
        System.out.printf("当前时间与格林威治时间差 %s \n", zonedDateTime.getOffset());
        System.out.printf("当前时间的纽约时间 %s \n", instant.atZone(ZoneId.of("America/New_York")));

    }
}
