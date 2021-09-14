package com.lx.demo;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo
 * @Description:
 * 执行顺序:
 *
 * 构造块
 * 静态块s2
 * 构造块
 * 构造块
 *
 * 静态块：用static申明，JVM加载类时执行，仅执行一次
 * 构造块：类中直接用{}定义，每一次创建对象时执行, 构造块优先与构造方法
 *
 * 1.静态块其实就是给类初始化的，而构造代码块是给对象初始化的。
 * 2.静态代码块只会运行一次, 一个类中可以有多个静态代码块，执行顺序按照位置决定。
 * 3.静态块中的变量是局部变量，与普通函数中的局部变量性质没有区别。
 *
 * 当涉及到继承时，按照如下顺序执行：
 *     执行父类的静态代码块，并初始化父类静态成员变量
 *     执行子类的静态代码块，并初始化子类静态成员变量
 *     执行父类的构造代码块，执行父类的构造函数，并初始化父类普通成员变量
 *     执行子类的构造代码块， 执行子类的构造函数，并初始化子类普通成员变量
 * @date 2021/9/14 下午6:48
 */
public class StaticBlockOrder {
    public static String text = "s2";

    public static StaticBlockOrder s1 = new StaticBlockOrder();

    static {
        System.out.println("静态块" + text);
    }

    public static StaticBlockOrder s3 = new StaticBlockOrder();

    {
//        构造块优先于构造方法
        System.out.println("构造块");
    }

    public StaticBlockOrder() {
//        System.out.println("构造方法");
    }

    public static void main(String[] args) {
        final StaticBlockOrder s4 = new StaticBlockOrder();
    }

}
