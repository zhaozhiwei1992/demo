package com.lx.demo;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Locale;

/**
 * @Description 大佬写点东西吧
 * @auther lx7ly
 * @create 2019-10-20 下午1:06
 */
public class DateTimeFormaterDemo {

    public static void main(String[] args) {
        final LocalDateTime now = LocalDateTime.now();
        final String format = DateTimeFormatter.ISO_DATE_TIME.format(now);
        System.out.printf("当前时间格式化 %s\n", format);

        final DateTimeFormatter dateTimeFormatterLong = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
        // 默认使用local语言环境
        final String formatLong = dateTimeFormatterLong.format(now);
        System.out.printf("格式化后 %s\n", formatLong);

        //使用long  Exception in thread "main" java.time.DateTimeException: Unable to extract value: class java.time
        // .LocalDateTime
        //https://coderanch.com/t/650546/java/Time-API-DateTimeFormatter-Exception-LocalDateTime
//        final String formatByFrench = dateTimeFormatterLong.withLocale(Locale.FRENCH).format(now);
        final String formatByFrench = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                .withLocale(Locale.FRENCH).format(now);
        System.out.printf("通过french格式化 %s\n", formatByFrench);

        //日其转字符串
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm:ss");
        final String customFormat = dateTimeFormatter.format(now);
        System.out.printf("自定义日期格式化 %s \n", customFormat);

        //字符串转日期
        final LocalDate parse = LocalDate.parse("2019-10-20");
        System.out.printf("2019-10-20转换为日期 %s \n", parse);

        //字符串转日期
        final DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
        final LocalDate parse2 = LocalDate.parse("20191020", yyyyMMdd);
        System.out.printf("2019-10-20转换为日期2 %s \n", parse2);

        //自定义日期格式转换, 如果时datetime那么日期时间就得都有，否则报错
        final ZonedDateTime parse1 = ZonedDateTime.parse("1990-08-07 12:12:00-0400"
                , DateTimeFormatter.ofPattern("yyyy-MM" + "-dd HH:mm:ssxx"));
        System.out.printf("自定义日期字符串转日期 %s \n", parse1);

        final LocalDate parse3 = LocalDate.parse("1990-08-07", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.printf("自定义日期字符串转日期 %s \n", parse3);

        final Instant start = Instant.now();
        Date from = Date.from(start);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format1 = simpleDateFormat.format(from);
        System.out.println("Instant格式化" + format1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 将Instant转换为LocalDateTime
        LocalDateTime dateTime = LocalDateTime.ofInstant(start, ZoneId.systemDefault());
        System.out.println("Instant格式化2:" + formatter.format(dateTime));
    }
}
