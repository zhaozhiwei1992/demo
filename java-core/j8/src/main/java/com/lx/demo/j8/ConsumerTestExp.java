package com.lx.demo.j8;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * consumer是j8提供的一种模板方式，不要去继承, 只要符合规范都可以是consumer
 *
 * 只进不出
 */
public class ConsumerTestExp {

    public static void main(String[] args) {

        //最low方式
        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer i) {
                System.out.println(i);
            }
        };
        Arrays.asList(1, 2, 3, 4, 5).forEach(consumer);

        Arrays.asList(1, 2, 3, 4, 5).forEach(System.out::println);
        //上面为该方式的简写形式
        Arrays.asList(1, 2, 3, 4, 5).forEach((Integer i) -> System.out.println(i));

        //and then
        // 这里输出了四次，
        Consumer<String> consumerStr = System.out::println;
        consumerStr.accept("hello"); //hello
        consumerStr // world
                .andThen(consumerStr) //world
                .andThen(ConsumerTestExp::echo) //echo world
                .accept("world");
    }

    public static void echo(String msg){
        System.out.printf("echo %s: \n", msg);
    }

}
