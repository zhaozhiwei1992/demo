package com.lx.demo;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title: null.java
 * @Package: com.lx.demo.cycledep
 * @Description: 模拟spring循环依赖简单版
 * 参考: https://mp.weixin.qq.com/s?__biz=MzI4Njc5NjM1NQ==&mid=2247492278&idx=1&sn=fa35a3fbe88f6a94384d99fb32798cd2&chksm=ebd5dd9adca2548c90ef6027dc0ee975c5cd9679aa64220ee9ee89dfee3a8208e789ab8213a2&mpshare=1&scene=24&srcid=0612OPPaBsxx0wQlzIbf6Ktb&sharer_sharetime=1591957203580&sharer_shareid=8018bd6a4424e9835bf198243116344d&poc_token=HPNg_mejZYk3ZrhAuDG7dK9MxjLRcucGFUdKBemj
 * @author: zhaozhiwei
 * @date: 2025/4/15 22:14
 * @version: V1.0
 */
public class InitBean {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        // 理想情况的循环依赖处理, 单例, 只能有1个A和1个B
        // 初始化a, 发现需要b, 那么就去初始化b
        A a = new A();
        // 初始化b, 发现需要a, a就在上边，虽然是半个(未完全初始化，a中的b为null)
        B b = new B();
        // b.a 指向a(引用), 此时a是半个，因为a中的b为null
        b.a = a;
        // a.b 指向 b, 此时a就完整了(指向了b的引用)，上边b.a因为是引用类型，也同时完整了, 那下边b同时也完整了
        a.b = b;
        System.out.println(b.getA() == a);
        System.out.println(a.getB() == b);

        // 如果通过spring 没有new的方式怎么搞
        // 读取spring中待初始化的类
        Class<?>[] classes = new Class[]{A.class, B.class};
        // 初始化bean
        for (Class<?> aClass : classes) {
            getBean(aClass);
        }

        // 检验
        System.out.println(getBean(B.class).getA() == getBean(A.class));
        System.out.println(getBean(A.class).getB() == getBean(B.class));
    }

    static Map<String, Object> cacheMap = new HashMap<>();

    private static <T> T getBean(Class<T> aClass) throws InstantiationException, IllegalAccessException {
        // 判断bean是否存在

        String beanName = aClass.getSimpleName().toLowerCase();
        if(cacheMap.containsKey(beanName)){
            return (T) cacheMap.get(beanName);
        }
        // 初始化bean
        // new A();
        Object o = aClass.newInstance();
        // 设置属性: 这里属性可能循环依赖bean, 上述硬编码是bean在上下文存在，所以这里也得存储下来,用个map
        cacheMap.put(beanName, o);
        for (Field field : o.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Class<?> type = field.getType();
            // 依赖的bean名
            String lowerCase = type.getSimpleName().toLowerCase();
            if(cacheMap.containsKey(lowerCase)){
                field.set(o, cacheMap.get(lowerCase));
            }else{
                //new B(); 此时A会引用到半个B
                field.set(o, getBean(type));
            }
        }
        return (T) o;
    }

    /**
     * @Title: null.java
     * @Package: com.lx.demo.cycledep
     * @Description: TODO
     * @author: zhaozhiwei
     * @date: 2025/4/15 22:13
     * @version: V1.0
     */
    public static class B {
        public A a;

        public A getA() {
            return a;
        }
    }

    /**
     * @Title: null.java
     * @Package: com.lx.demo.cycledep
     * @Description: TODO
     * @author: zhaozhiwei
     * @date: 2025/4/15 22:13
     * @version: V1.0
     */
    public static class A {
        public B b;

        public B getB() {
            return b;
        }
    }
}
