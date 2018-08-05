package com.lx.demo.javabeans;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * spring 獲取bean數據轉換, 一堆的字符串如何轉換成各自對應的類型
 */
public class SpringPropertyEditorDemo {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
        context.setConfigLocation("context.xml");
        context.refresh();

        User user = context.getBean("user", User.class);

        System.out.println(user);
    }
}
