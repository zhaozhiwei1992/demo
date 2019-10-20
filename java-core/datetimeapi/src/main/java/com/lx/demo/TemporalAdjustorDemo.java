package com.lx.demo;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

/**
 * 周期性日期
 */
public class TemporalAdjustorDemo {

    public static void main(String[] args) {
        // 某个月的第一个周二
        final LocalDate localDate = LocalDate.of(2020, 9, 1)
                .with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY));
        System.out.println(localDate);

        //计算下一个工作日, 一周七天 6, 7不属于工作日
        final LocalDate with = LocalDate.of(2020, 9, 1).with(w -> {
            LocalDate result = (LocalDate) w;
            do {
                result = result.plusDays(1);
            } while (result.getDayOfWeek().getValue() >= 6);
//            result = result.plusDays(7);
            return result;
        });
        System.out.printf("下一个工作日 %s", with);
    }
}
