package com.lx.demo.j8;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.stream.Stream;

public class FileFilter {
    public static void main(String[] args) {
        listDir("/tmp");
        beforeJ8Filter();
        j8Lambada();
        j8Filter();
    }

    /**
     * 列出当前子目录
     * @param path
     */
    public static void listDir(String path){
        final File file = new File(path);
        final File[] files = file.listFiles(curfile -> {
            return curfile.isDirectory();
        });

        Stream.of(files).sorted((file1, file2) -> {
            return file1.compareTo(file2);
        }).forEach(System.out::println);
    }

    /**
     * java8之前得文件过滤
     */
    public static void beforeJ8Filter(){
        System.out.println("java8之前过滤文件");
        File[] files = new File(".").listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return file.isHidden();
            }
        });

        for (File file : files) {
            System.out.println(file);
        }
    }

    /**
     *  java8 + lambada
     * (file, s) -> file.isHidden() 表示输入参数为file, s, 返回值为boolean得一个方法
     *
     */
    public static void j8Lambada(){
        System.out.println("java8+lambada过滤文件");
        File[] files = new File(".").listFiles((file, s) -> file.isHidden());
        Arrays.asList(files).forEach(file -> System.out.println("file = " + file));
    }

    /**
     * stream遍历数组
     */
    public static void j8Filter(){
        System.out.println("java8+stream过滤文件");
        File[] files = new File(".").listFiles(File::isHidden);
        Stream.of(files).forEach(file -> System.out.println("file = " + file));
    }

}
