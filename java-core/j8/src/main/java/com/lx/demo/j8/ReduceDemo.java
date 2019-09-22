package com.lx.demo.j8;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * 聚合操作
 */
public class ReduceDemo {
    public static void main(String[] args) {
        // 下面三种计算方式都可以, 其中第一种加入第一参数后就不用optional了， 因为肯定不为空
        final Integer reducesum = Stream.of(1, 2, 3, 4, 5).reduce(0, (x, y) -> x + y);
        final Optional<Integer> reduceOptional = Stream.of(1, 2, 3, 4, 5).reduce((x, y) -> x + y);
        final Optional<Integer> integerOptional = Stream.of(1, 2, 3, 4, 5).reduce(Integer::sum);
        assert reduceOptional.get().equals(integerOptional.get()) && reducesum.equals(reduceOptional.get());
        System.out.printf("大家的值都为 %s \n", reducesum);

        // 对对象的某个属性求和，例如对字符串长度求和
        final int i =
                Stream.of("zhangsan", "lisi", "wangwu").mapToInt(String::length).sum();
        final Integer reduce = Stream.of("zhangsan", "lisi", "wangwu").reduce(0,
                (total, word) -> total + word.length(), (total1, total2) -> total1 + total2);
        assert  i == reduce;
        System.out.printf("获取所有字符串长度之和: %s\n", i);
    }
}
