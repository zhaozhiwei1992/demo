package com.lx.demo.resourcelocation.file;

import org.junit.Test;
import sun.misc.IOUtils;

import java.io.*;
import java.net.URL;

public class FileLoader {

    @Test
    public void testAbsolutePath() throws IOException {
        File file = new File("/home/lx7ly/workspace/demo/resourcelocation/src/main/resources/application.properties");
        FileReader fileReader = new FileReader(file);

        char[] a = new char[50];
        fileReader.read(a); // 读取数组中的内容
        System.out.println(a);
    }

    /**
     * 使用相对路径支持不同环境
     * 使用url统一定位 支持不同协议
     * getURLStreamHandler
     * @throws IOException
     */
    @Test
    public void testRelativePath() throws IOException {

        URL resource = Thread.currentThread().getContextClassLoader().getResource("application.properties");
//        URL resource = this.getClass().getClassLoader().getResource("application.properties");
        InputStream inputStream = resource.openStream();
        byte[] bytes = new byte[50];
        int read = inputStream.read(bytes);
//        for (byte aByte : bytes) {
//            System.out.println((char)aByte);
//        }

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        System.out.println(bufferedReader.readLine());
    }
}
