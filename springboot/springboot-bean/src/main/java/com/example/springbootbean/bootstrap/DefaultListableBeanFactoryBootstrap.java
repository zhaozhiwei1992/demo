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

        //根据Xml配置文件创建Resource资源对象，该对象中包含了BeanDefinition的信息
        final ClassPathResource classPathResource = new ClassPathResource("META-INF/configurable-context.xml");
//        实现了BeanDefinitionRegistry接口
        final DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
//        BeanDefinitionRegistry -> DefaultListableBeanFactory, 使用基础的ioc容器需要自己制定reader
        final XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(factory);

        //XmlBeanDefinitionReader执行载入BeanDefinition的方法，最后会完成Bean的载入和注册。
        //完成后Bean就成功的放置到IOC容器当中，以后我们就可以从中取得Bean来使用
        xmlBeanDefinitionReader.loadBeanDefinitions(classPathResource);
        String name = String.valueOf(factory.getBean("name"));
        System.out.println(name);
//        factory.destroySingletons();

//       如果销毁bean下次get会重新创建
        name = String.valueOf(factory.getBean("name"));
        System.out.println(name);
    }
}
