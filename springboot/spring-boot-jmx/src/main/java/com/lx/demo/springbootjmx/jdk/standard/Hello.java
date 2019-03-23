package com.lx.demo.springbootjmx.jdk.standard;

import javax.management.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 标准方式必须实现mbean接口， 否则
 * Exception in thread "main" javax.management.NotCompliantMBeanException:
 * MBean class com.lx.demo.springbootjmx.jdk.standard.Hello does not implement DynamicMBean,
 * and neither follows the Standard MBean conventions (javax.management.NotCompliantMBeanException:
 * Class com.lx.demo.springbootjmx.jdk.standard.Hello is not a JMX compliant Standard MBean)
 * nor the MXBean conventions (javax.management.NotCompliantMBeanException: com.lx.demo.springbootjmx.jdk.standard.Hello:
 * Class com.lx.demo.springbootjmx.jdk.standard.Hello is not a JMX compliant MXBean)
 *
 * 增加发布订阅消息
 *
 * 发布消息后通过jconsole的订阅按钮，就可以看到改变
 * @see NotificationBroadcasterSupport 这个用来发布消息 使用 javax.management.NotificationBroadcasterSupport#sendNotification(javax.management.Notification)
 *
 * 如果要在程序
 * @see NotificationFilter 过滤消息是否被订阅
 * @see NotificationListener 程序订阅消息
 */
public class Hello extends NotificationBroadcasterSupport implements HelloMBean, NotificationListener, NotificationFilter {

    public Hello() {
        //一定要设置监听器， 不然不会接收消息的
        this.addNotificationListener(this, this, null);
    }

    public String getMsg() {
        System.out.println("get: " + msg);
        return msg;
    }

    private static final AtomicLong sequenceNumber = new AtomicLong();

    public void setMsg(String msg) {
        System.out.println("set: " + msg);

        //修改后要发布消息
        Notification notification = new AttributeChangeNotification(this,
                sequenceNumber.incrementAndGet(),
                System.currentTimeMillis(),
                "Data has been changed from " + this.msg + " to " + msg,
                "msg",
                String.class.getName(),
                this.msg,
                msg
        );

//      在jconsole客户端中 点击订阅就可以看到变更信息
        sendNotification(notification);

        this.msg = msg;
    }

    private String msg;

    public String sayHello(){
        System.out.println("helloworld msg is: " + this.msg);
        return msg;
    }

    /**
     *
     * @param notification
     * @return
     */
    @Override
    public boolean isNotificationEnabled(Notification notification) {
        // 直关心 AttributeChangeNotification 通知，并且仅限于字段/属性名称为 "data"
        if (AttributeChangeNotification.class.isAssignableFrom(notification.getClass())) {
            AttributeChangeNotification attributeChangeNotification = AttributeChangeNotification.class.cast(notification);
            if ("msg".equals(attributeChangeNotification.getAttributeName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 数据改变后触发订阅
     * @param notification
     * @param handback
     */
    @Override
    public void handleNotification(Notification notification, Object handback) {
        AttributeChangeNotification attributeChangeNotification = (AttributeChangeNotification) notification;
        String oldData = (String) attributeChangeNotification.getOldValue();
        String newData = (String) attributeChangeNotification.getNewValue();
        System.out.printf("The notification of data's attribute  - old data : %s , new data : %s \n", oldData, newData);
    }
}
