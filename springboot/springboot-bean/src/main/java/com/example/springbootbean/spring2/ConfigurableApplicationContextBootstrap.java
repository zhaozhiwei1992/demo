package com.example.springbootbean.spring2;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * {@see https://docs.spring.io/spring/docs/4.3.23.RELEASE/spring-framework-reference/htmlsingle/#beans}
 * {@see https://github.com/mercyblitz/thinking-in-spring-boot-samples/blob/master/spring-framework-samples/spring-framework-2.0.x-sample/src/main/java/thinking/in/spring/boot/samples/spring2/bootstrap/ConfigurableApplicationContextBootstrap.java}
 */
public class ConfigurableApplicationContextBootstrap {

    static{
        // 调整系统属性 "env"，实现 "name" bean 的定义切换
        // envValue 可能来自于 "-D" 命令行启动参数
        // 参数当不存在时，使用 "prod" 作为默认值

        //https://www.cnblogs.com/yangmingke/p/6058898.html
        //-D 参数是java虚拟机参数，需要设置到vmoption -Denv=dev 这样就是走开发
        // 等价于 在配置文件中设置 set JAVA_OPTS=-Denv=dev
        String envValue = System.getProperty("env", "prod");
        System.setProperty("env", envValue);
    }

    public static void main(String[] args) {
        //打开上下文
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("META-INF/configurable-context.xml");
        String name = String.valueOf(classPathXmlApplicationContext.getBean("name"));
        System.out.println(name);

        //关闭上下文
        classPathXmlApplicationContext.close();
    }
}
