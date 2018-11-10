package com.lx.demo.j8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class FunctionTestExp {
    public static void main(String[] args) {
        map(Arrays.asList("zhangsan", "lisi", "wangwu", "zhaoliu"), new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return s.length();
            }
        }).forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                System.out.println(integer);
            }
        });
        //String::length == (String s) -> s.length()
//        map(Arrays.asList("zhangsan", "lisi", "wangwu", "zhaoliu"), (String s) -> s.length()).forEach(System.out::println);
        map(Arrays.asList("zhangsan", "lisi", "wangwu", "zhaoliu"), String::length).forEach(System.out::println);
    }

    /**
     * 将list<String> 映射为 list<String.length()>
     * @param list
     * @param function
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> List<R> map(List<T> list, Function<T, R> function) {
        ArrayList<R> rs = new ArrayList<>();
        for (T t : list) {
            rs.add(function.apply(t));
        }
        return rs;
    }
}
