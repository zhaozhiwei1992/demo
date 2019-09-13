package com.lx.demo.j8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class FunctionTestExp {

    public static void main(String[] args) {

        //function convert
        Function<String, Integer> stringToInt = Integer::parseInt;
        final Integer integer = stringToInt.apply("2");
        System.out.println(integer);

        Function<Integer, String> intToString = String::valueOf;
        final String str = intToString.apply(integer);
        System.out.println(str);

        // compose vs andthen
        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;

        // 先g 后 f
        // (V v) -> apply(before.apply(v))
        // first applies the function to its input, and then applies this function to the result
        Function<Integer, Integer> h = f.compose(g);
        System.out.printf("compose: %d \n", h.apply(1));

        // 先f 后g
        Function<Integer, Integer> h2 = f.andThen(g);
        System.out.printf("andthen: %d \n", h2.apply(1));

        //lambda$main$0
        CustomFunctional customFunctional = ()->{
            System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        };
        customFunctional.execute();

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

@FunctionalInterface
interface CustomFunctional{
    void execute();

    default String description(){
        return "this is description";
    }

}

