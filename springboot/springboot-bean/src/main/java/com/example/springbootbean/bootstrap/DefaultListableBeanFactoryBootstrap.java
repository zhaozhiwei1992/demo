package com.example.springbootbean.bootstrap;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;

public class DefaultListableBeanFactoryBootstrap {

    static{
        // 调整系统属性 "env"，实现 "name" bean 的定义切换
        // envValue 可能来自于 "-D" 命令行启动参数
        // 参数当不存在时，使用 "prod" 作为默认值

        //https://www.cnblogs.com/yangmingke/p/6058898.html
        //-D 参数是java虚拟机参数，需要设置到vmoption -Denv=dev 这样就是走开发
        // 等价于 在配置文件中设置 set JAVA_OPTS=-Denv=dev
        String envValue = System.getProperty("env", "dev");
        System.setProperty("env", envValue);
    }

    public static void main(String[] args) {
        final ClassPathResource classPathResource = new ClassPathResource("META-INF/configurable-context.xml");
//        实现了BeanDefinitionRegistry接口
        final DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
//        BeanDefinitionRegistry -> DefaultListableBeanFactory
        final XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(factory);
        xmlBeanDefinitionReader.loadBeanDefinitions(classPathResource);
        String name = String.valueOf(factory.getBean("name"));
        System.out.println(name);
//        factory.destroySingletons();

//       如果销毁bean下次get会重新创建
        name = String.valueOf(factory.getBean("name"));
        System.out.println(name);
    }
}
