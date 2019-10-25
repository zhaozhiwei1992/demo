package com.lx.demo.improvement;

import java.io.IOException;

/**
 * @Description 大佬写点东西吧
 * @auther lx7ly
 * @create 2019-10-25 下午8:58
 */
public class ProcessDemo {
    public static void main(String[] args) {
        final ProcessBuilder ls = new ProcessBuilder("ls", "-l");
        // 标准输出中加入当前目录列表
        final ProcessBuilder processBuilder = ls.inheritIO();
        try {
            processBuilder.start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
