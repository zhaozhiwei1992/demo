package com.lx.demo;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class ProcessIDDemo {
    public static void main(String[] args) {
        // Java 9 之前的实现
        getProcessIdBeforeJava9();
        getProcessIdInJava9();
        getProcessIdInJava10();
    }

    private static void getProcessIdBeforeJava9() {
        final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        final String name = runtimeMXBean.getName();
        //48830@archlinux
        System.out.println(name);
        final String pid = name.substring(0, name.indexOf("@"));
        System.out.printf("process id j9- %s \n", pid);
    }

    private static void getProcessIdInJava9() {
        final long pid = ProcessHandle.current().pid();
        System.out.printf("process id j9 %s \n", pid);
    }

    private static void getProcessIdInJava10() {
        final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        System.out.printf("process id j10 %s \n", runtimeMXBean.getPid());
    }
}
