package com.lx.demo.springbootjmx.jdk.dynamicImplByJDK;

import javax.management.DynamicMBean;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.StandardMBean;
import java.lang.management.ManagementFactory;

public class ManageBeanServer {
    public static void main(String[] args) throws Exception {

        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        ObjectName objectName = new ObjectName("com.lx.demo.springbootjmx.jdk.dynamicImplByJDK:type=Person");
        DynamicMBean mBean = new StandardMBean(new Coder(), Person.class);
        mBeanServer.registerMBean(mBean, objectName);

        System.out.println("com.lx.demo.springbootjmx.jdk.dynamicImplByJDK.ManageBeanServer staring.....");
        Thread.sleep(Long.MAX_VALUE);
    }
}
