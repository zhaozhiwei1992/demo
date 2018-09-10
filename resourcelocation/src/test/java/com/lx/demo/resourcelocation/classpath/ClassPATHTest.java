package com.lx.demo.resourcelocation.classpath;

import org.junit.Test;

import java.io.*;
import java.net.URL;

public class ClassPATHTest {

    /**
     * 测试访问自定义扩展class协议路径(基于java实现机制)
     */
    @Test
    public void testClassPATH() throws IOException {
        URL url = new URL("classpath://application.properties");
        InputStream inputStream = url.openStream();

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        System.out.println(bufferedReader.readLine());
    }
}
