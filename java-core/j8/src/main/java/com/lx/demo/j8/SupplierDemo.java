package com.lx.demo.j8;

import java.util.function.Supplier;

/**
 * {@link java.util.function.Supplier}
 *
 @FunctionalInterface
 public interface Supplier<T> {
 T get();
 }

 这玩意儿就是返回个方法，你可以从中取值，方法的实现方式更优雅
 */
public class SupplierDemo {

    public static void main(String[] args) {

        //普通的输出
        echo("helloworld");

        //如何实现过一秒再输出
        //原始的方式就是在方法前加一行代码，然后再调用echo
//        sleep(1000);
//        echo("helloworld");
        // j8+方式
        echo(()->{
            sleep(1000);
            return "hello world";
        });
    }

    public static void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void echo(String msg){
        System.out.printf("echo: %s \n", msg);
    }

    public static void echo(Supplier<String> supplier){
        System.out.printf("echo: %s", supplier.get());
    }
}
