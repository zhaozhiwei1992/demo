package com.lx.demo.lambda;

import java.util.function.Supplier;

/**
 * Java 1.4 added assertions to the language, with an assert keyword. Why were
 * assertions not supplied as a library feature? Could they be implemented as
 * a library feature in Java 8
 */
public class AssertMethod {
    public static void main(String[] args) {

        String s1 = "zhangsan";
        assertValue(()-> s1.length() < 5);
    }

    public static void assertValue(Supplier<Boolean> supplier){
        if(!supplier.get()){
            throw new AssertionError();
        }
    }
}
