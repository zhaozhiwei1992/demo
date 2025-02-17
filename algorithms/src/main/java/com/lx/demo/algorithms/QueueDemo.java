package com.lx.demo.algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Title: QueueDemo
 * @Package com/lx/demo/algorithms/QueueDemo.java
 * @Description:  队列测试
 * test1: 原生队列
 * test2: 链表实现队列
 * test3: 数组实现队列
 * @author zhaozhiwei
 * @date 2025/1/8 13:17
 * @version V1.0
 */
public class QueueDemo {
    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }

    private static void test3() {
        // 队列初始化
        ArrayQueue<Integer> queue = new ArrayQueue<>();
        for (int i = 0; i < 5; i++) {
            queue.offer(i);
        }

        // 访问首个元素
        Integer peek = queue.peek();
        System.out.println("peek:" + peek); //0, FIFO, 首元素就是最先入队的

        Integer size = queue.size();
        System.out.println("size:" + size); //5
        // 出队
        Integer poll = queue.poll();
        System.out.println("poll:" + poll);//0

        // 队列长度
        size = queue.size();
        System.out.println("size:" + size);//4

        // 判断是否为空
        System.out.println("empty:" + queue.isEmpty());
    }

    private static void test2() {
        // 队列初始化
        LinkedQueue<Integer> queue = new LinkedQueue<>();
        for (int i = 0; i < 5; i++) {
            queue.offer(i);
        }

        // 访问首个元素
        Integer peek = queue.peek();
        System.out.println("peek:" + peek); //0, FIFO, 首元素就是最先入队的

        Integer size = queue.size();
        System.out.println("size:" + size); //5
        // 出队
        Integer poll = queue.poll();
        System.out.println("poll:" + poll);//0

        // 队列长度
        size = queue.size();
        System.out.println("size:" + size);//4

        // 判断是否为空
        System.out.println("empty:" + queue.isEmpty());
    }

    private static void test1() {
        // 队列初始化
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            queue.offer(i);
        }

        // 访问首个元素
        Integer peek = queue.peek();
        System.out.println("peek:" + peek); //0, FIFO, 首元素就是最先入队的

        Integer size = queue.size();
        System.out.println("size:" + size); //5
        // 出队
        Integer poll = queue.poll();
        System.out.println("poll:" + poll);//0

        // 队列长度
        size = queue.size();
        System.out.println("size:" + size);//4

        // 判断是否为空
        System.out.println("empty:" + queue.isEmpty());
    }
}

// 链表实现队列
class LinkedQueue<T> {

    private ListNode<T> head;
    private ListNode<T> tail;

    private int size;

    // 当前值
    public T peek(){
        return head.val;
    }

    // 出队, 从头开始移除
    public T poll(){
        T peek = peek();
        head = head.next;
        size --;
        return peek;
    }

    // 入队
    public T offer(T t){
        ListNode<T> node = new ListNode<>(t);
        if(size >= 1){
            // 添加到队列最后边, 此时tail表示上个末节点
            tail.next = node;
        }else{
            head = node;
        }
        tail = node;
        size ++;
        return this.peek();
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }
}

// 通过数组实现队列
class ArrayQueue<T> {
    private ArrayList<T> list = new ArrayList<T>();

    public void offer(T t){
        list.add(t);
    }

    public T poll(){
        return list.remove(0);
    }

    public T peek(){
        return list.get(0);
    }

    public int size(){
        return list.size();
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }
}