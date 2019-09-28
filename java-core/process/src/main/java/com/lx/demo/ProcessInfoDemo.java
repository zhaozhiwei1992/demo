package com.lx.demo;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class ProcessInfoDemo {
    public static void main(String[] args) {

        final long pid = ProcessHandle.current().pid();
        System.out.printf("process id %s \n", pid);

        final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

        final Instant instant = Instant.ofEpochMilli(runtimeMXBean.getStartTime());
        // 9.0+
        final LocalDate localDate = LocalDate.ofInstant(instant, ZoneId.systemDefault());
        System.out.printf("process start time %s\n", localDate);
        System.out.printf("process up time %s \n", runtimeMXBean.getUptime());

        final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        System.out.printf("process thread count %s \n", threadMXBean.getThreadCount());
    }
}
