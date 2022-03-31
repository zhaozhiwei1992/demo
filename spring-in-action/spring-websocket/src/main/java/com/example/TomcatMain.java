package com.example;

import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import java.io.File;

/**
 * @Title: TomcatMain
 * @Package com/example/TomcatMain.java
 * @Description:
 * 自己启动容器, 相当与内嵌tomcat?
 * 通过api方式启动tomcat, 并且加载spring 容器初始化, 等价tomcat-plugin启动
 * 步骤:
 * 1. 引入tomcat依赖， 详见pom.xml
 * 2. 初始化tomcat, 并addWebApp, 如果是web项目会主动扫描/META-INF/services/下javax.servlet.ServletContainerInitializer的实现从而找到
 * org.springframework.web.SpringServletContainerInitializer
 * 3. 注意: HandlesTypes注解，上边要写接口
 * 参考：
 * https://blog.csdn.net/qq_35262405/article/details/102986008
 * https://blog.csdn.net/qq_35262405/article/details/102467479
 * @author zhaozhiwei
 * @date 2022/3/17 上午9:05
 * @version V1.0
 */
public class TomcatMain {

    public static void main(String[] args) throws LifecycleException, ServletException, ClassNotFoundException,
            InstantiationException, IllegalAccessException {

        final TomcatMain tomcatMain = new TomcatMain();

        // 获取当前资源目录
        final String classPath = tomcatMain.getClass().getResource("/").getPath();

        final Tomcat tomcat = new Tomcat();

        tomcat.setPort(9090);

        final Host host = tomcat.getHost();
        host.setName("localhost");
        // class 根目录
        host.setAppBase("webapps");

//        设置context, 主要是设置webapp目录
        String webappPath = System.getProperty("user.dir") + File.separator
                + "spring-web" + File.separator
                + "src" + File.separator
                + "main" + File.separator
                + "webapp";
        String contextPath = "/";
//        非web项目无法直接访问一些静态资源，如*.html、*.css文件
//        final Context context = tomcat.addContext("/", new File("src/main/webapp").getAbsolutePath());
//        如果我们使用addContext获得的Context对象，那么tomcat就不会回调我们的WebApplicationInitializer接口的实现类。
//        但是我们可以手动去加载web的生命周期，这样它虽然不是一个web项目，但是它依然可以去回调我们的WebApplicationInitializer接口的实现类。
//        context.addLifecycleListener((LifecycleListener) Class.forName(host.getConfigClass()).newInstance());

//      首先需要得到Context对象, 配置tomcat，  使用addWebApp表示这个是个web项目, 会自动去找META-INF.service/ spi信息
        final Context context = tomcat.addWebapp(contextPath, webappPath);
        if(context instanceof StandardContext){
            StandardContext standardContext = (StandardContext) context;

        }

        // 启动tom
        tomcat.start();

        // 关闭释放tomcat
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                try {
                    tomcat.stop();
                } catch (LifecycleException e) {
                    e.printStackTrace();
                }
            }
        });

        tomcat.getServer().await();

    }

    private void waitIndefinitely() {
        Object lock = new Object();
        synchronized(lock) {
            try {
                lock.wait();
            } catch (InterruptedException var5) {
                throw new Error("InterruptedException on wait Indefinitely lock:" + var5.getMessage(), var5);
            }

        }
    }

}
