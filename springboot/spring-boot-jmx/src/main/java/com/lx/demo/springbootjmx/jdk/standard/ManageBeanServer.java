package com.lx.demo.springbootjmx.jdk.standard;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * 基于jdk规范 实现标准得 mbean
 * 1. 它能管理的资源（包括属性，方法，时间）
 * 2. 必须定义在接口中，然后MBean必须实现这个接口。
 * 3. 它的命名也必须遵循一定的规范，例如我们的MBean为Hello，则接口必须为HelloMBean。
 */
public class ManageBeanServer {
    public static void main(String[] args) throws Exception {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        //要被管理得bean
        Hello hello = new Hello();
        ObjectName objectName = getObjectName(hello);
        mBeanServer.registerMBean(hello, objectName);

        System.out.println("com.lx.demo.springbootjmx.jdk.standard.ManageBeanServer staring.....");
        Thread.sleep(Long.MAX_VALUE);
    }

    private static ObjectName getObjectName(Object obj) throws MalformedObjectNameException {
//        return new ObjectName("com.lx.demo.springbootjmx.jdk:type=Hello");
        Class<?> aClass = obj.getClass();
        String packageName = aClass.getPackage().getName();
        String objName = aClass.getSimpleName();
        return new ObjectName(packageName + ":type=" + objName);
    }
}
