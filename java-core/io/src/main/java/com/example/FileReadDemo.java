package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * @Title: null.java
 * @Package: com.example
 * @Description: TODO
 * @author: zhaozhiwei
 * @date: 2022/10/26 上午11:01
 * @version: V1.0
 */
public class FileReadDemo {

    public static void main(String[] args) {

        String content;
        StringBuilder builder = new StringBuilder();

        File file = new File("/tmp" + File.separator + "javadoc.json");
        InputStreamReader streamReader = null;
        try {
            streamReader = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            while ((content = bufferedReader.readLine()) != null)
                builder.append(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (builder.length() > 0) {
            System.out.println(builder);
        }
    }
}
