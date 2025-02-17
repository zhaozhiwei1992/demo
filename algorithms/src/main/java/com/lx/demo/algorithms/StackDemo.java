package com.lx.demo.algorithms;

import java.util.ArrayList;
import java.util.Stack;

/**
 * @Title: StackDemo
 * @Package com/lx/demo/algorithms/StackDemo.java
 * @Description:
 * stack: FILO
 * test1: 测试原生stack
 * test2: 通过链表自定义实现stack
 * test3: 通过数组自定义实现stack
 * @author zhaozhiwei
 * @date 2025/1/8 10:38
 * @version V1.0
 */
public class StackDemo {
    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }

    private static void test3() {
        ArrayStack<Integer> integerStack = new ArrayStack<>();
        for (int i = 0; i < 5; i++) {
            integerStack.push(i);
        }
        Integer peek = integerStack.peek();
        System.out.println("peek:" + peek); //4
        Integer pop = integerStack.pop();
        System.out.println("pop:"  + pop); //4
        Integer peek2 = integerStack.peek();
        System.out.println("peek:" + peek2);//3
    }

    private static void test2() {
        LinkedStack<Integer> integerStack = new LinkedStack<>();
        for (int i = 0; i < 5; i++) {
            integerStack.push(i);
        }
        Integer peek = integerStack.peek();
        System.out.println("peek:" + peek); //4
        Integer pop = integerStack.pop();
        System.out.println("pop:"  + pop); //4
        Integer peek2 = integerStack.peek();
        System.out.println("peek:" + peek2);//3
    }

    private static void test1() {
        Stack<Integer> integerStack = new Stack<Integer>();
        for (int i = 0; i < 5; i++) {
            integerStack.push(i);
        }
        Integer peek = integerStack.peek();
        System.out.println("peek:" + peek); //4
        Integer pop = integerStack.pop();
        System.out.println("pop:"  + pop); //4
        Integer peek2 = integerStack.peek();
        System.out.println("peek:" + peek2);//3
    }
}

// 实现一个栈，先进先出
// 将新元素作为头节点，头插法，链表头插法, 单向链表不能往前走,头插实现栈更方便
class LinkedStack<T> {

    private ListNode<T> head;
    private int size;

    // 添加当前元素为头部，深度增加
    public T push(T t) {
        ListNode<T> listNode = new ListNode<>(t);
        if (size >= 1) {
            listNode.next = head;
        }
        head = listNode;
        size ++;
        return head.val;
    }

    // 移除头部返回下一个，深度减小
    public T pop() {
        T obj = this.peek();
        head = head.next;
        size --;
        return obj;
    }

    // 返回当前头部
    public T peek() {
        return head.val;
    }
}

// 通过数组实现栈, 数组有下标，移动标记即可
// 动态数组  ArrayList
class ArrayStack<T> {

    private ArrayList<T> array = new ArrayList<>();

    private int headIndex = -1;

    // 添加当前元素为头部，深度增加
    public T push(T t) {
        array.add(t);
        headIndex ++;
        return array.get(headIndex);
    }

    // 移除头部返回下一个，深度减小
    public T pop() {
        T obj = this.peek();
        array.remove(headIndex);
        headIndex --;
        return obj;
    }

    // 返回当前头部
    public T peek() {
        return array.get(headIndex);
    }
}