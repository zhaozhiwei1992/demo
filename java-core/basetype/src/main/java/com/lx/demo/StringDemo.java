package com.lx.demo;

import java.lang.reflect.Field;

/**
 * Hello world!
 *
 * 谁说string不可变
 */
public class StringDemo {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        String hello = "hello ";
        String world = "world";

        System.out.println(hello);
        System.out.println(world);

        // 1.5+ 反射修改string
        final char[] chars = "ttang".toCharArray();
        final Field field = String.class.getDeclaredField("value");
        field.setAccessible(true);
        field.set(world, chars);
        System.out.println(hello);
        System.out.println(world);
    }
}
