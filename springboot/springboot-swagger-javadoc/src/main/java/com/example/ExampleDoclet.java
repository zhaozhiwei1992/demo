package com.example;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.Doclet;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.RootDoc;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: ExampleDoclet
 * @Package com/example/springbootcache/ExampleDoclet.java
 * @Description: 获取注释信息, 运行main方法即可
 * @date 2022/10/25 下午8:25
 */
public class ExampleDoclet extends Doclet {
    public static boolean start(RootDoc root) {
        ClassDoc[] classes = root.classes();
        for (ClassDoc cls : classes) {
            System.out.println(cls);
            // 输出类注释信息
            System.out.println(cls.getRawCommentText());
            MethodDoc[] methods = cls.methods();
            for (MethodDoc meth : methods) {
                System.out.println(meth);
                // 输出方法上注释信息
                System.out.println(meth.getRawCommentText());
            }
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        String[] docArgs =
                new String[]{
//                        "-doclet", ExampleDoclet.class.getName(), "这个参数是你需要解析的.java文件的绝对路径","如果你需要一次解析两个
//                        .java文件，可以继续在这后面添加绝对路径"
                        "-doclet", ExampleDoclet.class.getName(), "/home/zhaozhiwei/workspace/demo/springboot" +
                        "/springboot-swagger-javadoc/src/main/java/com/example/resource" +
                        "/PersonResource.java"
                };
        com.sun.tools.javadoc.Main.execute(docArgs);
    }
}