package com.lx.demo.j8;

import java.util.concurrent.Callable;

public class Test {

    public static void main(String args[]) throws Exception {
        System.out.println(fetch().call());
    }

    public static Callable<String> fetch(){
        return ()->"hello";
    }
}
