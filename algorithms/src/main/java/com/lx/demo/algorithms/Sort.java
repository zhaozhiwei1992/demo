package com.lx.demo.algorithms;

/**
 * 排序接口　接手指定类型
 * @param <T>
 */
public interface Sort<T extends Comparable<T>>{

    /**
     * 排序要素
     * @param values
     */
    void sort(T[] values);

    /**
     * 返回要素数组
     * @param values
     * @param <T>
     * @return
     */
    static <T> T[] of(T ... values){
        return values;
    }
}
