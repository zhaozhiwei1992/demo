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
        final LocalDate localDate = LocalDate.of(2020, 10, 16).with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        System.out.println(localDate);

        final LocalDate with = LocalDate.of(2020, 10, 16).with(w -> {
            LocalDate result = (LocalDate) w;
//            do {
//                result = result.plusDays(1);
//            } while (result.getDayOfWeek().getValue() >= 6);
            result = result.plusDays(7);
            return result;
        });
        System.out.printf("自定义周期性日期 %s", with);
    }
}
