package com.lx.demo;

import java.util.HashMap;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo
 * @Description: 测试integer修改int值会不会影响原值
 * 结果,
 *  每次值变更产生新对象，
 *  private final int value;// 参考Integer源码实现，int为final表示这玩意儿不可改，所以引用没啥意义
 * @date 2022/1/6 上午10:40
 */
public class IntegerDemo {

    public static void main(String[] args) {
        // 引用测试
        final HashMap<String, Integer> map = new HashMap<>();
        // 1
        Integer integer = new Integer(1);
        map.put("xx", integer);
        System.out.println(System.identityHashCode(integer));
        integer += 1; //不改变
        // integer自增加会产生新的对象
        System.out.println(System.identityHashCode(integer));
        System.out.println(map.get("xx"));
        // 126
        integer = new Integer(126);
        map.put("xx", integer);
        integer += 1; //不改变
        System.out.println(map.get("xx"));
        Integer integer127 = 127;
        System.out.println(System.identityHashCode(integer) == System.identityHashCode(integer127));// 127地址相同
        // 666666
        integer = new Integer(6666);
        map.put("xx", integer);
        integer += 1; //不改变
        Integer integer6667 = 6667;

//        public static Integer valueOf(int i) {
//            if (i >= Integer.IntegerCache.low && i <= Integer.IntegerCache.high)
//                return Integer.IntegerCache.cache[i + (-Integer.IntegerCache.low)];
//            return new Integer(i);
//        }
        System.out.println(System.identityHashCode(integer) == System.identityHashCode(integer6667));// 超过127地址不同
        System.out.println(map.get("xx"));
    }
}
