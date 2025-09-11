package com.lx.demo;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class LocalDateDemo {
    public static void main(String[] args) {
        final LocalDate now = LocalDate.now();
        System.out.printf("当前日期, %s\n", now.getDayOfMonth());
        System.out.println(now);

        System.out.printf("当前月, %s \n", now.getMonthValue());
//        自动补0
        System.out.printf("当前月补0, %s \n", DateTimeFormatter.ofPattern("MM").format(now));

        final LocalDate xBirthDay = LocalDate.of(1990, Month.AUGUST, 07);
        System.out.printf("birthday, %s \n", xBirthDay);
        System.out.printf("1990年 %s 为闰年\n", xBirthDay.isLeapYear());
        System.out.printf("2020年的生日日期 %s \n", xBirthDay.plus(Period.ofYears(Year.now().getValue() - 1989)));

        // 时间段
        final long until = xBirthDay.until(LocalDate.now(), ChronoUnit.DAYS);
        System.out.printf("x birthday到现在时间 %s天\n", until);

        System.out.printf("今年的程序员日 %s \n", LocalDate.of(Year.now().getValue(), 1, 1).plusDays(255));

        System.out.printf("1月31号下一个月 %s\n", LocalDate.of(2020, 1, 31).plusMonths(1));

        System.out.printf("3月31号上一个月 %s \n", LocalDate.of(2020, 3, 31).minusMonths(1));

        System.out.printf("周五再过三天是 %s \n", DayOfWeek.FRIDAY.plus(3));

        System.out.printf("当前月五号 %s \n", now.withDayOfMonth(5));

        // 跳过周六周天
        System.out.printf("当前开始下一个工作日的日期 %s \n", now.with(w -> {
            LocalDate result = (LocalDate) w;
            do {
                result = result.plusDays(1);
            } while (result.getDayOfWeek().getValue() >= 6);
            return result;
        }));
    }
}
