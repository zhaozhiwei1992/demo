package com.lx.demo.springbootjmx.spring;

import org.springframework.jmx.export.annotation.*;
import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import org.springframework.stereotype.Component;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import java.util.concurrent.atomic.AtomicLong;

/**
 * spring托管后，验证通过controller修改bean属性后， jconsole是否会跟着变化
 *
 * 这个objectName跟标准java方式new一样， objname要符合规范， 否则
 * Caused by: javax.management.MalformedObjectNameException: Key properties cannot be empty
 * @see ManagedResource 标识这个是一个mbean
 * @see NotificationPublisherAware 通过spring来发布事件
 */
@ManagedResource(
        objectName = "com.lx.demo.springbootjmx.spring.Person:type=Person",
        description = "这个bean被spring托管"
)
@Component(value = "person")
public class Person implements NotificationPublisherAware {
    private long id;
    private String name;
    private int age;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManagedAttribute(defaultValue = "糖人人", description = "这是名字")
    public String getName() {
        return name;
    }

    private static final AtomicLong sequenceNumber = new AtomicLong();

    public void setName(String name) {

        //修改后要发布消息
        Notification notification = new AttributeChangeNotification(this,
                sequenceNumber.incrementAndGet(),
                System.currentTimeMillis(),
                "Data has been changed from " + this.name + " to " + name,
                "msg",
                String.class.getName(),
                this.name,
                name
        );
        this.publisher.sendNotification(notification);

        this.name = name;
    }

    @ManagedAttribute(defaultValue = "18")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @ManagedOperation(description = "display 操作")
    public String display() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    private NotificationPublisher publisher;

    /**
     * 事件发布者
     * @param notificationPublisher
     */
    @Override
    public void setNotificationPublisher(NotificationPublisher notificationPublisher) {
        this.publisher = notificationPublisher;
    }
}

