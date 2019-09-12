package com.lx.demo.j8;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.IntBinaryOperator;
import java.util.stream.Stream;

/**
 * 简单lambada测试
 * 参考: http://www.runoob.com/java/java8-lambda-expressions.html
 */
public class LambadaSimple {

    public static void main(String[] args) {

        final String[] strings = new String[]{"zhang", "wang", "li", "zhao"};
        Arrays.sort(strings, (x, y) -> Integer.compare(x.length(), y.length()));
        Arrays.sort(strings, Comparator.comparingInt(String::length));
        Stream.of(strings).forEach(System.out::println);

        LambadaSimple lambadaSimple = new LambadaSimple();

        //四则运算
        //　传统方式需要通过　模板方法模式同事实现多个操作类，　现在只需用的时候构建一个运算器
        //加法器
        MathOperation addition = (int a, int b) -> a + b;
        int i = addition.applyAsInt(1, 2);
        System.out.println(i);

        //减法器
        IntBinaryOperator subtraction = (int a, int b) -> a - b;
        i = subtraction.applyAsInt(5, 3);
        System.out.println(i);

        // 传统方式加法器
        final MathOperation addition2 = new MathOperation() {
            @Override
            public int applyAsInt(int left, int right) {
                return left + right;
            }
        };
        int i1 = addition2.applyAsInt(1, 6);
        System.out.println(i1);

        //sayHello
        Consumer<String> stringConsumer = (String message) -> {
            System.out.println("hello: " + message);
        };

        // 类型可推导Consumer<String>，　参数类型可省略
        stringConsumer = message -> {
            System.out.printf("hello: %s \n", message);
        };

        stringConsumer.accept("李四"); //一次
        stringConsumer.andThen(stringConsumer).andThen(stringConsumer).accept("李四"); //多次

//        final Consumer<String> consumer = new Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                System.out.println("hello " + s);
//            }
//
//            @Override
//            public Consumer<String> andThen(Consumer<? super String> after) {
//                return new Consumer<String>() {
//                    @Override
//                    public void accept(String s) {
//                        //这里死循环
//                        accept(s);
//                        after.accept(s);
//                    }
//                };
//            }
//        };
//
//        final Consumer<String> consume2 = new Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                System.out.println("hello2 " + s);
//            }
//        };
//        consumer.andThen(consume2).accept("李狗蛋");

        //

        final Consumer<String> stringConsumer1 = (String message) -> {
            System.out.println("hello " + extparam + " " + message);
        };
        stringConsumer1.accept("666");

    }

    final static String extparam = "hhh";

    /**
     * {@link IntBinaryOperator}
     */
    @FunctionalInterface
    interface MathOperation{
        int applyAsInt(int left, int right);
    }
}
