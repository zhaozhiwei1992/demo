package com.lx.demo;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo
 * @Description:
 * 测试引用传递
 *
 * 如果引用的对象一直没变，那它就一直是值传递, java中没有指针的概念，基本无法引用传递
 *
 * 证明java中没有引用传递
 * @date 2022/5/9 下午8:00
 */
public class ReferencePassingTest {

    public static void main(String[] args) {
//        定义一个对象, 看通过方法形参传递后能否改变原引用
        Object a = new A();
        ((A)a).setValue("我是A");
        change(a);
//        如果不改变对象内容,这里输出我是A则证明java中认为的值传递是成立的
//        也可以输出对象地址
        System.out.printf("这个是main方法输出 %s \n a对象地址 %s \n", a, System.identityHashCode(a));
    }

    /**
     * @data: 2022/5/9-下午8:05
     * @User: zhaozhiwei
     * @method: change
      * @param o :
     * @return: void
     * @Description: 传个参数a进去随便玩,　重新赋值，看会改成b不
     */
    private static void change(Object o) {
//        这里可以改变堆中对象内容, 这种事普遍理解为引用传递的误区
//        ((A)o).setValue("我是张三丰");
        System.out.printf("改变前形参地址，应与main中a一致, 地址 %s \n", System.identityHashCode(o));
        o = new B();
        ((B)o).setValue("我是b");
        System.out.printf("这个是change方法输出 %s \n, 形参o改变后对象地址 %s \n", o, System.identityHashCode(o));
    }

}

class A{

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "A{" +
                "value='" + value + '\'' +
                '}';
    }

    private String value;
}

class B{

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "B{" +
                "value='" + value + '\'' +
                '}';
    }

    private String value;
}
