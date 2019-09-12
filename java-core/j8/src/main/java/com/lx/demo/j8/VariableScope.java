package com.lx.demo.j8;

public class VariableScope {
    public static void main(String[] args) {
        echo("hello world", 10);
    }

    /**
     * 输出信息若干次
     * @param msg
     * @param count
     */
    public static void echo(String msg, int count){

        // 这里在runnable函数中并没有定义　count msg变量，也可以使用外部变量
        final Runnable customFunctional = () -> {
            for (int i = 0; i < count; i++) {
                // 这里不能修改闭包中的属性，
//                count++;
                System.out.printf("echo %s\n", msg);
                Thread.yield();
            }
        };
        new Thread(customFunctional).start();
    }
}
