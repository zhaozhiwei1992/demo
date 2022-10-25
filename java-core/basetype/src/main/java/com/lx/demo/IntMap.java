package com.lx.demo;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

import java.util.BitSet;
import java.util.stream.IntStream;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo
 * @Description: 比较通过bitmap和普通存储方式内存占用大小
 * 以数字为例子
 *
 * 普通数字存储,
 *
 * java.util.BitSet
 * https://blog.csdn.net/u010979642/article/details/91128400
 *
 *
采用 set 集合实现， 1亿个int类型整数占用空间是 4亿字节 大约是 400MB
1亿 int 整数 = 4字节 * 1 亿 = 4亿字节/1024/1024M=381.46M

采用位图实现， 因为 一个int类型是32位(4字节x8)， 所以占用的空间是 set 方式的 1/32
即 400/32=12.5MB

比如有一堆数字，需要存储，source=[3,5,6,9]
用int就需要4*4个字节。
java.util.BitSet可以存true/false。
如果用java.util.BitSet，则会少很多，其原理是：
1，先找出数据中最大值maxvalue=9
2，声明一个BitSet bs,它的size是maxvalue+1=10
3，遍历数据source，bs[source[i]]设置成true.
最后的值是：
(0为false;1为true)
index   0 1 2 3 4 5 6 7 8 9
bs     [0,0,0,1,0,1,1,0,0,1]
3, 5,6, 9

这样一个int型(如:9)需要占4字节共32位的数字, 现在只用了1位(9现在作为游标, 值是1表示存在, 1现在用2进制位表示)！比例32:1

https://codejiao.blog.csdn.net/article/details/125457236?spm=1001.2101.3001.6650.2&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-2-125457236-blog-54951472.pc_relevant_multi_platform_whitelistv3&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-2-125457236-blog-54951472.pc_relevant_multi_platform_whitelistv3&utm_relevant_index=3

 * @date 2022/10/18 下午9:20
 */
public class IntMap {

    public static void main(String[] args) {

        // 初始化1亿个数字
        int[] numbers = initNumbersNormal(10_000_000);
        System.out.println(numbers.length);
        // 内存大小
//        基本类型 长度(字节) the size of an object
//        Int 　　　 4 　　 16
//        Short 　　 2 　　 16
//        Long 　　 8 　　 24
//        Byte 　　 1 　　 16
//        Character 1 　　 16
//        Float 　　 4 　　 16
//        Double 　 8 　　 24
        // int类型的内存大小
        // 16
        System.out.println(ObjectSizeCalculator.getObjectSize(1));
        // 16
        System.out.println(ObjectSizeCalculator.getObjectSize(10_000_000));

        long objectSize = ObjectSizeCalculator.getObjectSize(numbers);
        //10_000_000个数字的内存大小 38M   40000016
        System.out.println(objectSize);
        System.out.println(objectSize/1024/1024 + "M");

        // 查找数字是否存在? 遍历判断
        final long l = System.currentTimeMillis();
        for (Integer number : numbers) {
            if(number.equals(9_999_999)){
                break;
            }
        }
        System.out.println("搜索耗时: " + (System.currentTimeMillis() - l));


        // 通过BitMap存储
        final IntMap intMap = new IntMap(numbers.length);
        for (int number : numbers) {
            intMap.add(number);
        }
        objectSize = ObjectSizeCalculator.getObjectSize(intMap);
        //10_000_000个数字的内存大小 38M   40000016
        System.out.println(objectSize);
        //true
        final long l1 = System.currentTimeMillis();
        System.out.println(intMap.contains(9_000_000));
        // 位图搜索很快, 判断值是否存在
        System.out.println("搜索耗时: " + (System.currentTimeMillis() - l1));
        // false
        System.out.println(intMap.contains(90_000_000));

        // 减少存储, 加快搜索判断是否存在
        final BitSet bitSet = new BitSet();
        // 默认初始化 64位, 可以表示0~63 这64个数字, 内占用64位,8字节 , 如果普通int存储, 那需要256字节   1:32
        //BITS_PER_WORD = 1 << ADDRESS_BITS_PER_WORD
        System.out.println(bitSet.size());
        System.out.println(ObjectSizeCalculator.getObjectSize(bitSet));

    }

    // 普通方式初始化数组
    private static int[] initNumbersNormal(int size) {
        return IntStream.iterate(1, item -> item + 1).limit(size).toArray();
    }

    // 一个int 32位, 则可以表示0~31数字的是否存在, 具体表示数字的范围可以设置，但是最多32个, 因为int数组每个值可以转换为2进制, 2进制位是1表示游标对应数字存在, 反之不存在
    private final int[] ints;

    public IntMap() {
        this.ints = new int[93750000];
    }

    public IntMap(int size) {
        this.ints = new int[size];
    }

    public void add(long i) {
        int r = (int)(i / 32L);
        int c = (int)(i % 32L);
        this.ints[r] |= 1 << c;
    }

    public boolean contains(long i) {
        int r = (int)(i / 32L);
        int c = (int)(i % 32L);
        return (this.ints[r] >>> c & 1) == 1;
    }
}
