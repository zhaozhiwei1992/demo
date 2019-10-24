package com.lx.demo.improvement;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @Description 大佬写点东西吧
 * @auther lx7ly
 * @create 2019-10-24 下午8:10
 */
public class FileDemo {
    public static void main(String[] args) {
        getPassword();
        dir();
        tree();
    }

    private static void dir() {
        // 单层目录
        try(DirectoryStream<Path> paths = Files.newDirectoryStream(Paths.get("/tmp"))){
            for (Path path : paths) {
                System.out.println(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取文件列表
     * 深度优先
     */
    private static void tree() {
        try(final Stream<Path> walk = Files.walk(Paths.get("/home/lx7ly/workspace/demo/java-core"))){
            walk.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getPassword() {
        //读取文件内容
        //java8的方式不会自动关闭资源，通过try-with-resource方式处理
        try(Stream<String> lines = Files.lines(Paths.get("/tmp/text.txt"))
                .onClose(() -> System.out.println("流关闭.."))
                .filter(s -> s.contains("password"))){
            assert lines != null;
            final Optional<String> password = lines.findFirst();
            password.ifPresent(s -> System.out.printf("获取密码 %s\n", s.split(":")[1]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
