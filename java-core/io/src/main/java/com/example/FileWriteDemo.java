package com.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @Title: null.java
 * @Package: com.example
 * @Description: TODO
 * @author: zhaozhiwei
 * @date: 2022/10/26 上午10:16
 * @version: V1.0
 */
public class FileWriteDemo {
    public static void main(String[] args) throws IOException {
        fileWrite();

        fileOutPutStream();

        fileOutPutStreamAppend();
    }

    /**
     * @date: 2022/10/26-上午10:19
     * @author: zhaozhiwei
     * @method: fileOutPutStreamAppend

     * @return: void
     * @Description: 追加文件
     */
    private static void fileOutPutStreamAppend() throws IOException {
        FileOutputStream fos = new FileOutputStream("E:/log.txt", true);
//true表示在文件末尾追加
        String str = "hello world!";
        fos.write(str.getBytes());
        fos.close();
    }

    private static void fileOutPutStream() throws IOException {
        File txt = new File("E:/log1.txt");
        if (!txt.exists()) {
            txt.createNewFile();
        }
        String str = "hello world!";
        byte bytes[] = new byte[512];
        bytes = str.getBytes();
        int b = bytes.length;   //是字节的长度，不是字符串的长度
        FileOutputStream fos = new FileOutputStream(txt);
        fos.write(bytes, 0, b);
        fos.write(bytes);
        fos.close();
    }

    private static void fileWrite() {
        String str = "hello world!";
        FileWriter writer;
        try {
            writer = new FileWriter("E:/token.txt");

            writer.write("");//清空原文件内容
            writer.write(str);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
