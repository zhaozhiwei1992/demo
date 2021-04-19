package com.lx.demo;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

/**
 * Hello world!
 *
 * 谁说string不可变
 */
public class StringDemo {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        //编码就是把字符转换为字节，而解码是把字节重新组合成字符。
        String str1 = "中文";
        //编码
        final byte[] bytes = str1.getBytes(StandardCharsets.UTF_8);
        // 解码
        final String s = new String(bytes, StandardCharsets.UTF_8);
        System.out.println(s);

        //针对999 补0
        String str = String.format("%6d", 999).replace(" ", "0");
        final String format = String.format("%06d", 99);//其中0表示补零而不是补空格，6表示至少6位
        System.out.println("补0 : " + str);
        System.out.println("补0 : " +format);

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
