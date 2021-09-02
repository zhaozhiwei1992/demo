package com.example.springbootbean.bootstrap;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;

/**
 * {@see https://docs.spring.io/spring/docs/4.3.23.RELEASE/spring-framework-reference/htmlsingle/#beans}
 * {@see https://github.com/mercyblitz/thinking-in-spring-boot-samples/blob/master/spring-framework-samples/spring-framework-2.0.x-sample/src/main/java/thinking/in/spring/boot/samples/spring2/bootstrap/XMLApplicationContextBootstrap.java}
 */
public class FileSystemXMLApplicationContextBootstrap {

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

    /**
     *
     * @param args
     * org.springframework.core.env.EnvironmentSystemIntegrationTests#fileSystemXmlApplicationContext
     */
    public static void main(String[] args) throws IOException {
        //需要设置并刷新
        // 构建 XML 配置驱动 Spring 上下文
        final ClassPathResource classPathResource = new ClassPathResource("META-INF/configurable-context.xml");
        File tmpFile = File.createTempFile("test", "xml");
        FileCopyUtils.copy(classPathResource.getFile(), tmpFile);

        // strange - FSXAC strips leading '/' unless prefixed with 'file:'
        ConfigurableApplicationContext ctx =
                new FileSystemXmlApplicationContext(new String[] {"file:" + tmpFile.getPath()}, false);
        ctx.refresh();
        // 启动上下文
        ctx.refresh();

        String name = String.valueOf(ctx.getBean("name"));
        System.out.println(name);

        //关闭上下文
        ctx.close();
    }
}
