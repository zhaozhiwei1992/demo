package com.lx.demo.springbootjmx.spring;

import org.springframework.stereotype.Component;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;

/**
 * 通过api的方式注入到spring上下文中, 参考
 * https://docs.spring.io/spring/docs/4.3.22.RELEASE/spring-framework-reference/htmlsingle/#jmx-notifications-listeners
 *
 */
@Component
public class NotificationListenImpl implements NotificationListener, NotificationFilter {
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
            if ("name".equals(attributeChangeNotification.getAttributeName())) {
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
