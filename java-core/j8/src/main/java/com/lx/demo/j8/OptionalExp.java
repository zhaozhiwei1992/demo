package com.lx.demo.j8;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Optional 类是一个可以为null的容器对象。如果值存在则isPresent()方法会返回true，调用get()方法会返回该对象。
 * Optional 是个容器：它可以保存类型T的值，或者仅仅保存null。Optional提供很多有用的方法，这样我们就不用显式进行空值检测。
 * Optional 类的引入很好的解决空指针异常。
 */
public class OptionalExp {

    public static void main(String[] args) {

        //optional中的flutte风格
        final Optional<String> s = getTestOptional().flatMap(TestOptional::hello);
        // ==> 等价
        final Optional<String> hello = getTestOptional().get().hello();

        //有参数更刺激, 通过optional的方式求一个数的平方根
        //普通方式
        final Double aDouble = revers(4.0).get();
        final Optional<Double> squareRoot = OptionalExp.squareRoot(aDouble);
        System.out.println(squareRoot.get());

        //文艺方式, stream中的flatMap和optional中类似，可以把optional理解为带有一个value的stream, 那么flatMap就相当于是
        //把内部optional的value拿出来撸, 就像脱掉了optional或者stream的外衣，只留下内部的操作
        final Optional<Double> squareRoot2 =
                Optional.of(4.0).flatMap(OptionalExp::revers).flatMap(OptionalExp::squareRoot);
        System.out.printf("优雅方式获取4.0的平方根 %s\n", squareRoot2.get());


        //创建optional
        Optional<Double> num = revers(1.0);
        System.out.println(num.get());

        num.ifPresent(n -> {
            System.out.printf("如果有值计算 %s \n ", n+2.0);
        });

        Integer value1 = null;
        Integer value2 = Integer.valueOf(10);

        // Optional.ofNullable - 允许传递为 null 参数
        Optional<Integer> a = Optional.ofNullable(value1);

        // Optional.of - 如果传递的参数是 null，抛出异常 NullPointerException
        Optional<Integer> b = Optional.of(value2);

//        Exception in thread "main" java.util.NoSuchElementException
//        a.orElseThrow(NoSuchElementException::new);
        Integer intA = a.orElse(Integer.valueOf(0));
        //需要时候调用
        Integer intAGet = a.orElseGet(() -> Integer.valueOf(0));
        Integer intB = b.get();
        System.out.println(intA + intB + intAGet);
    }

    /**
     * 返回一个包装testoptional对象的optional
     * @return
     */
    public static Optional<TestOptional> getTestOptional(){
        return Optional.of(new TestOptional());
    }

    /**
     * 将一个double对象转换为optional
     * @param v
     * @return
     */
    private static Optional<Double> revers(Double v) {
        return v==0?Optional.empty():Optional.of(v);
    }

    /**
     * 平方根
     * @param v
     * @return
     */
    private static Optional<Double> squareRoot(Double v) {
        return v < 0?Optional.empty() : Optional.of(Math.sqrt(v));
    }
}

class TestOptional{
    public Optional<String> hello(){
        final String methodName = Thread.currentThread().getStackTrace()[0].getMethodName();
        System.out.printf("请求到了 %s\n", methodName);
        return Optional.of(methodName);
    }
}
