package com.lx.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

class XXUtil<E extends Number>{
    /**
     * 测试泛型推导
     * @param list
     * @param <T>
     * @return
     */
    public <T extends Number> T testT(List<T> list){
        return list.get(0);
    }

    public static <T extends Number> T testTStatic(List<T> list){
        return list.get(0);
    }
}

public class GenericWildcardsTypeDemo {

    public static void main(String[] args) {

        final List<String> strings = new ArrayList<>();
//        testT(strings); //会检测到类型不匹配

        // 使用对象方式竟然没有判断出类型不匹配
        final XXUtil xxUtil = new XXUtil();
        xxUtil.testT(strings);
        // 静态方法直接报错，类型不匹配
//        XXUtil.testTStatic(strings);

        List<Number> numbers = new ArrayList<>();
        upperBoundedWildcards(numbers);
        unboundedWildcards(numbers);
        lowerBoundedWildcards(numbers);
    }

    /**
     * 测试泛型推导
     * @param list
     * @param <T>
     * @return
     */
    public static <T extends Number> T testT(List<T> list){
        return list.get(0);
    }

    private static void upperBoundedWildcards(List<Number> numbers) {
        // 泛型上界通配配型
        // Number -> Byte, Short , Integer, Long
        numbers.add(Byte.valueOf((byte) 1));
        numbers.add(Short.valueOf((short) 2));
        numbers.add(Integer.valueOf(3));
        numbers.add(Long.valueOf(4L));

        List<Byte> bytes = Arrays.asList((byte) 5);
        List<Short> shorts = Arrays.asList((short) 6);
        List<Integer> integers = Arrays.asList(7);

        numbers.addAll(bytes); // ? extends Number; List<Byte>
        numbers.addAll(shorts); // ? extends Number; List<Short>
        numbers.addAll(integers); // ? extends Number; List<Integer>

        // 被操作（处理）的对象，需要更为抽象类型，Number
        // 待整合（输入）的对象，可以是具体类型

        upperBoundedWildcardsDemo(numbers, System.out::println);
    }

    private static void unboundedWildcards(List<Number> numbers) {
        // 完全通配类型
        // 在运行时与非统配泛型会出现方法签名冲突
        // 然后完全通配类型，可以适配任意类型，比如集合
        // 反而具体类型泛型会限制类型范围
        unboundedWildcardsDemo(numbers);
    }

    private static void lowerBoundedWildcards(List<Number> numbers) {
        lowerBoundedWildcardsDemo(numbers, numbers);
    }

    private static void lowerBoundedWildcardsDemo(List<? extends Number> producer, List<? super Number> consumer) {
        // PECS stands for producer-extends, consumer-super.
        // 读取数据（生产者）使用 extends
        for (Number number : producer) {

        }
        // 操作输出（消费者）使用 super
        consumer.add(1);
        consumer.add((short) 2);
    }


    public static void unboundedWildcardsDemo(Iterable<?> iterable) { // 泛型类型擦写，导致和下方法冲突
        for (Object e : iterable) {
            System.out.println(e);
        }
    }

//    public static void unboundedWildcardsDemo(Iterable<Object> iterable) {
//        for (Object e : iterable) {
//            System.out.unboundedWildcardsDemo(e);
//        }
//    }

    public static void upperBoundedWildcardsDemo(Iterable<? extends Number> iterable, Consumer<Object> consumer) {
        for (Object e : iterable) {
            consumer.accept(e);
        }

    }
}
