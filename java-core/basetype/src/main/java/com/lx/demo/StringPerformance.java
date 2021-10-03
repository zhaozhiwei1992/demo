package com.lx.demo;

/**
 * @Title: StringPerformance
 * @Package com/lx/demo/StringPerformance.java
 * @Description: 性能测试, String, StringBuffer, StringBuilder
 * stringTest 1000 times, cost: 11
 * stringConcatTest 1000 times, cost: 0
 * stringBuffer 1000 times, cost: 1
 * stringBuilderTest 1000 times, cost: 0
 * stringTest 10000 times, cost: 690
 * stringConcatTest 10000 times, cost: 1
 * stringBuffer 10000 times, cost: 1
 * stringBuilderTest 10000 times, cost: 0
 * stringTest 100000 times, cost: 59279
 * stringConcatTest 100000 times, cost: 2
 * stringBuffer 100000 times, cost: 3
 * stringBuilderTest 100000 times, cost: 2
 * 数量级很大时, String拼接影响很大, 如果循环次数动态很多, 不能使用string拼接
 * @author zhaozhiwei
 * @date 2021/10/3 下午12:12
 * @version V1.0
 */
public class StringPerformance {

    public static void main(String[] args) {
        final StringPerformance stringPerformance = new StringPerformance();
        String key = "ttang";

        int times = 1000;
        for (int i = 0; i < 3; i++) {
            stringPerformance.stringTest(key, times);
            stringPerformance.stringConcatTest(key, times);
            stringPerformance.stringBuffer(key, times);
            stringPerformance.stringBuilderTest(key, times);
            times *= 10;
        }
    }

    public void stringTest(String key, int times){
        final long l = System.currentTimeMillis();
        String s = new String("");
        for (int i = 0; i < times; i++) {
            s += key;
        }
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + times + " times, cost: " + (System.currentTimeMillis() - l));
    }

    public void stringBuilderTest(String key, int times){

        final long l = System.currentTimeMillis();
        StringBuilder s = new StringBuilder("");
        for (int i = 0; i < times; i++) {
            s.append(key);
        }

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + times + " times, cost: " + (System.currentTimeMillis() - l));
    }

    public void stringBuffer(String key, int times){

        final long l = System.currentTimeMillis();
        StringBuffer s = new StringBuffer("");
        for (int i = 0; i < times; i++) {
            s.append(s);
        }

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + times + " times, cost: " + (System.currentTimeMillis() - l));
    }

    public void stringConcatTest(String key, int times){
        final long l = System.currentTimeMillis();
        String s = new String("");
        for (int i = 0; i < times; i++) {
            s = s.concat(s);
        }
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + times + " times, cost: " + (System.currentTimeMillis() - l));
    }
}
