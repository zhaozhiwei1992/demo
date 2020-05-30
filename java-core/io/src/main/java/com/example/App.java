package com.example;

import java.io.File;
import java.net.URL;
import java.util.TreeSet;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        // 获取编译根目录
        URL url = Thread.currentThread().getContextClassLoader().getResource("/");
        System.out.println(url);

        if (url == null) {
//            跟上边classloader等价
            url = App.class.getResource("/");
        }


        String docPath = url.getPath() + "/"
                .replaceAll("\\.", "/");

        docPath = docPath.replaceAll("//", "/");
        System.out.println(docPath);
        TreeSet<String> filenames = new TreeSet<String>();
        final File file1 = new File(docPath);
        if (file1 != null) {
            for (File file : file1.listFiles()) {
                filenames.add(file.getName());
            }
        }
        System.out.println(filenames);

        // 当前类(class)所在的包目录
        System.out.println(App.class.getResource(""));

        // class path根目录
        System.out.println(App.class.getResource("/"));

//        null
        System.out.println(App.class.getResource("business/001-create_base_table.sql"));

    }
}
