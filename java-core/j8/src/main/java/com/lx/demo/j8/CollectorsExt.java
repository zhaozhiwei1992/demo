package com.lx.demo.j8;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * @Title: null.java
 * @Package: com.lx.demo.j8
 * @Description: 扩展java8 Collectors
 * 1. 扩展summingBigDecimal
 * @author: zhaozhiwei
 * @date: 2023/4/6 下午4:29
 * @version: V1.0
 */
public class CollectorsExt {

    @FunctionalInterface
    interface ToBigDecimalFunction<T> {
        BigDecimal applyAsBigDecimal(T value);
    }

    /**
     * @data: 2023/4/6-下午4:17
     * @User: zhaozhiwei
     * @method: summingBigDecimal
     * @param mapper :
     * @return: java.util.stream.Collector<T,?,java.math.BigDecimal>
     * @Description: 仿造原java实现summingInt搞个BigDecimal版
     */
    public static <T> Collector<T, ?, BigDecimal> summingBigDecimal(ToBigDecimalFunction<? super T> mapper) {
        return new CollectorImpl<>(() -> new BigDecimal[1], (a, t) -> {
            if (a[0] == null) {
                a[0] = BigDecimal.ZERO;
            }
            a[0] = a[0].add(mapper.applyAsBigDecimal(t));
//            System.out.println("第一个: " + a[0]);
        }, (a, b) -> {
            a[0] = a[0].add(b[0]);
//            System.out.println("第二个: " + a[0]);
            return a;
        }, a -> a[0], Collections.emptySet());
    }

    static class CollectorImpl<T, A, R> implements Collector<T, A, R> {
        private final Supplier<A> supplier;
        private final BiConsumer<A, T> accumulator;
        private final BinaryOperator<A> combiner;
        private final Function<A, R> finisher;
        private final Set<Characteristics> characteristics;

        CollectorImpl(Supplier<A> supplier,
                      BiConsumer<A, T> accumulator,
                      BinaryOperator<A> combiner,
                      Function<A, R> finisher,
                      Set<Characteristics> characteristics) {
            this.supplier = supplier;
            this.accumulator = accumulator;
            this.combiner = combiner;
            this.finisher = finisher;
            this.characteristics = characteristics;
        }

        CollectorImpl(Supplier<A> supplier,
                      BiConsumer<A, T> accumulator,
                      BinaryOperator<A> combiner,
                      Set<Characteristics> characteristics) {
            this(supplier, accumulator, combiner, castingIdentity(), characteristics);
        }

        private static <I, R> Function<I, R> castingIdentity() {
            return i -> (R) i;
        }

        @Override
        public BiConsumer<A, T> accumulator() {
            return accumulator;
        }

        @Override
        public Supplier<A> supplier() {
            return supplier;
        }

        @Override
        public BinaryOperator<A> combiner() {
            return combiner;
        }

        @Override
        public Function<A, R> finisher() {
            return finisher;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return characteristics;
        }
    }

}

