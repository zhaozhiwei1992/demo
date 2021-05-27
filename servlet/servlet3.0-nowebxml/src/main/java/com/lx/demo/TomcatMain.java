package com.lx.demo;

import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.ContextConfig;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import java.io.File;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo
 * @Description:
 * 根据tomcat等插件，学习直接通过tomcat api来进行容器启动
 * 基本上所有的java工具都不会脱离最根本的java控制， 有唯一的入口
 *
 *1. 先查看通过插件启动的servlet-1.0-SNAPSHOT-war-exec.jar中manifest.mf文件，确定main方法
 * Manifest-Version: 1.0
 * Main-Class: org.apache.tomcat.maven.runner.Tomcat7RunnerCli
 *
 * 2. 引入插件作为依赖, 找到实现
 *
 * 参考: tomcat.conf下 server.xml
 *
 * @date 2021/5/26 上午9:30
 */
public class TomcatMain {

    public static void main(String[] args) throws LifecycleException, ServletException {

        final TomcatMain tomcatMain = new TomcatMain();

        // 获取当前资源目录
        final String classPath = tomcatMain.getClass().getResource("/").getPath();

        final Tomcat tomcat = new Tomcat();

//        tomcat.setPort(9090);

        final Host host = tomcat.getHost();
        host.setName("localhost");
        // class 根目录
        host.setAppBase("webapps");

//        设置context, 主要是设置webapp目录
        String webappPath = System.getProperty("user.dir") + File.separator
                + "src" + File.separator
                + "main" + File.separator
                + "webapp";
        String contextPath = "/";
        final Context context = tomcat.addWebapp(contextPath, webappPath);
        if(context instanceof StandardContext){
            StandardContext standardContext = (StandardContext) context;
            // 设置默认webxml
            standardContext.setDefaultWebXml(new File(classPath, "conf/web.xml").getPath());

            // 设置class目录, 标准web项目目录，CLASS编译后放到了WEB-INF/classes下，硬启动class和web分离，需独立制定
            final Wrapper echoServlet = tomcat.addServlet(contextPath, "EchoServlet", new EchoServlet());
            echoServlet.addMapping("/echo");
        }

        // web.xml中基本所有项都可以硬编码, 下述操作，可以使web同时支持8080 /8081请求
//                <Connector port="8080" protocol="HTTP/1.1"
//        connectionTimeout="20000"
//        redirectPort="8443" />
        final Connector connector = new Connector();
        connector.setPort(8081);
        connector.setProtocol("HTTP/1.1");
//        connector.setURIEncoding("utf-8");
        tomcat.getService().addConnector(connector);


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

//       hode住防止主线程释放
//        tomcatMain.waitIndefinitely();
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
