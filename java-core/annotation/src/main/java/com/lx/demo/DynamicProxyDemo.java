package com.lx.demo;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface Service{
    void echo(String msg);
}

class ServiceImplProxy implements InvocationHandler {

    public Service getService() {
        return service;
    }

    /**
     * 设置被代理对象
     * @param service
     */
    public void setService(Service service) {
        this.service = service;
    }

    private Service service;

    /**
     * 返回一个代理实现
     * @return
     */
    public Object getInstance(){
        final Class<? extends Service> clazz = this.service.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    /**
     * 代理逻辑
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("调用代理类方法之前");
        method.invoke(this.service, args);
        System.out.println("调用代理类方法之后");
        return proxy;
    }
}

class ServiceImpl implements Service{

    @Override
    public void echo(String msg) {
        System.out.println("我要找代理了哈哈");
    }
}

/**
 * 动态代理输出日志
 */
public class DynamicProxyDemo {
    public static void main(String[] args) {
        //@see sun.misc.ProxyGenerator
        // 这个玩意儿生成以后会到项目根目录, <=1.8之前写法, 但是没法指定目录
//        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        // >1.8后写法
//        System.setProperty("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");
        // 指定目录写法
        saveProxyFile();
        final ServiceImpl service = new ServiceImpl();
        final Class<? extends Service> clazz = service.getClass();
        Service service1 = (Service) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(String.format("调用 %s 之前", method.getName()));
                Object obj = method.invoke(service, args);
                System.out.println(String.format("调用 %s 之后", method.getName()));
                return obj; //返回调用结果
            }
        });
        service1.echo("hh");
    }

    private static void saveProxyFile() {
        String path = Service.class.getResource("").getPath();
        System.out.println(path);
        FileOutputStream out = null;
        try {
            byte[] classFile = ProxyGenerator.generateProxyClass("$Proxy0", Service.class.getInterfaces());
            out = new FileOutputStream(path + "$Proxy0.class");
            out.write(classFile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
