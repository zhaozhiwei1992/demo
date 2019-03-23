package com.lx.demo.springbootjmx.jdk.dynamic;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class ManageBeanServer {
    public static void main(String[] args) throws Exception {

        // MBean 服务器 - Agent Level
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        DynamicMBean mBean = new Coder();

        // 注册名称
        ObjectName objectName = getObjectName(mBean);

        // 注册 MBean
        mBeanServer.registerMBean(mBean, objectName);

        System.out.println("com.lx.demo.springbootjmx.jdk.dynamic.ManageBeanServer staring.....");
        Thread.sleep(Long.MAX_VALUE);
    }

    private static ObjectName getObjectName(Object obj) throws MalformedObjectNameException {
        Class<?> aClass = obj.getClass();
        String packageName = aClass.getPackage().getName();
        String objName = aClass.getSimpleName();
        return new ObjectName(packageName + ":type=" + objName);
    }

}
