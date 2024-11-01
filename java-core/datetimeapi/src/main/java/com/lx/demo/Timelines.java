package com.lx.demo;

import java.time.Duration;
import java.time.Instant;

/**
 *
 */
public class Timelines {

    public static void main(String[] args) {
        timelinelt8();
        timelinegt8();
    }

    /**
     * j8加入的api
     */
    private static void timelinegt8() {
        final Instant start = Instant.now();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final Instant end = Instant.now();
        final Duration timeElapsed = Duration.between(start, end);
        System.out.printf("耗时 %s\n", timeElapsed.toMillis());

        System.out.println(timeElapsed.multipliedBy(1).minus(Duration.between(start, end)).isZero());
    }

    private static void timelinelt8() {
        final long start = System.currentTimeMillis();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final long end = System.currentTimeMillis();
        System.out.printf("使用时间 %s\n", end-start);
    }
}
