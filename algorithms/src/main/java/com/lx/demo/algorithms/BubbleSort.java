package com.lx.demo.algorithms;

import java.util.Arrays;

/**
 * 时间复杂度
 * 若文件的初始状态是正序的，一趟扫描即可完成排序。所需的关键字比较次数 和记录移动次数 均达到最小值： ， 。
 * 所以，冒泡排序最好的时间复杂度为O(n)
 * 　　若初始文件是反序的，需要进行 趟排序。每趟排序要进行 次关键字的比较(1≤i≤n-1)，且每次比较都必须移动记录三次来达到交换记录位置。
 * 在这种情况下，比较和移动次数均达到最大值：
 *
 *
 * 冒泡排序的最坏时间复杂度为 。 O(n2)
 * 综上，因此冒泡排序总的平均时间复杂度为 O(n2)
 * 算法稳定性
 * 冒泡排序就是把小的元素往前调或者把大的元素往后调。比较是相邻的两个元素比较，交换也发生在这两个元素之间。
 * 所以，如果两个元素相等，是不会再交换的；如果两个相等的元素没有相邻，
 * 那么即使通过前面的两两交换把两个相邻起来，这时候也不会交换，所以相同元素的前后顺序并没有改变，所以冒泡排序是一种 稳定排序算法。
 * @param <T>
 */
public class BubbleSort<T extends Comparable<T>> implements Sort<T>{

    @Override
    public void sort(T[] values) {

        //需要比较values.length趟
        for (int i = 0; i < values.length; i++) {

            //每趟比较 values.length-i-1次
            //每次只跟剩下的比，每一趟都会往最后放一个当前趟的最大值，前面的肯定是小的
            // values.length-i-1, 这里ｊ必须减少１，否则与j+1比较会越界
            for (int j = 0; j < values.length-i-1; j++) {

                //如果前数大于后数交换
                if(values[j].compareTo(values[j + 1]) > 0){
                    //方法1
                    T temp = values[j];
                    values[j] = values[j+1];
                    values[j+1] = temp;
                    //方法2, 不需要临时变量, 通过求和方式计算仅限int
                }
            }
            System.out.printf("第%d轮：%s\n", i + 1, Arrays.toString(values));
        }
    }

    public static void main(String[] args) {
        System.out.println("一般情况");
        Integer[] values = Sort.of(3, 1, 2, 5, 4);
        Sort<Integer> sort = new BubbleSort<>(); // Java 7 Diamond 语法
        sort.sort(values);
        System.out.printf("排序结果：%s\n", Arrays.toString(values));

        System.out.println("完全逆序");
        values = Sort.of(5, 4, 3, 2, 1);
        sort = new BubbleSort<>();
        sort.sort(values);
        System.out.printf("排序结果：%s\n", Arrays.toString(values));
    }
}
