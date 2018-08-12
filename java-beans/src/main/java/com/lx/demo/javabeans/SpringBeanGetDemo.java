package com.lx.demo.javabeans;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SpringBeanGetDemo {
    public static void main(String[] args) throws NamingException {

        //spring bean获取方式1
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext();
        classPathXmlApplicationContext.setConfigLocation("context.xml");
        classPathXmlApplicationContext.refresh();
        User user = classPathXmlApplicationContext.getBean("user", User.class);
//        System.out.println(user);

        /**
         *
         <bean id="custom" class="org.springframework.beans.factory.config.CustomEditorConfigurer">
         <property name="propertyEditorRegistrars">
         <list>
         <bean class="com.lx.demo.javabeans.SpringEditorRegistry"></bean>
         </list>
         </property>
         </bean>
         下面的处理方式， user中的特殊类型比如date还是不能处理
         */
        //方式2
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //使用编程方式注册bean
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        /**
         *
         <bean id="user" class="com.lx.demo.javabeans.User">
         <property name="id" value="11"></property>
         <property name="name" value="lisi"></property>
         <property name="date" value="2018-08-03"></property>
         </bean>
         */
        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
        mutablePropertyValues.addPropertyValue("id", 2L);
        mutablePropertyValues.addPropertyValue("name", "ligoudan");
        beanDefinition.setPropertyValues(mutablePropertyValues);
        beanFactory.registerBeanDefinition("user", beanDefinition);
        //获取bean可以传入构造参数，bean中必须有对应的构造方法
//        user = beanFactory.getBean(User.class,1, "zhangsan");
        user = beanFactory.getBean(User.class);

        //利用xml注册bean
//        new XmlBeanDefinitionReader(beanFactory).loadBeanDefinitions(new ClassPathResource("context.xml"));
//        user = (User) beanFactory.getBean("user");
        System.out.println(user);


        //ejb bean获取方式
//        InitialContext context = new InitialContext();
//        Object user1 = context.lookup("user");
//        System.out.println(user1);
    }
}
