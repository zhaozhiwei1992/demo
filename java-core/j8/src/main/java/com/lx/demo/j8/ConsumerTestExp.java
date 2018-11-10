package com.lx.demo.j8;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ConsumerTestExp {

    public static void main(String[] args) {
        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer i) {
                System.out.println(i);
            }
        };
        Arrays.asList(1, 2, 3, 4, 5).forEach(consumer);

        Arrays.asList(1, 2, 3, 4, 5).forEach(System.out::println);
//        Arrays.asList(1, 2, 3, 4, 5).forEach((Integer i) -> System.out.println(i));
    }
}
