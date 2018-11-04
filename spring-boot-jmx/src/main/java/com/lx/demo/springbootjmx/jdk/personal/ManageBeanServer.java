package com.lx.demo.springbootjmx.jdk.personal;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * 基于jdk本身规范创建管理bean有很多限制
 */
public class ManageBeanServer {
    public static void main(String[] args) throws Exception {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = new ObjectName("com.lx.demo.springbootjmx.jdk:type=Hello");
        mBeanServer.registerMBean(new Hello(), objectName);

        System.out.println("com.lx.demo.springbootjmx.jdk.personal.ManageBeanServer staring.....");
        Thread.sleep(Long.MAX_VALUE);
    }
}
