package com.example;

import java.io.*;
import java.util.Objects;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example
 * @Description:
 * @date 2021/4/10 下午4:54
 */
public class FileDemo {
    public static void main(String[] args) {
//        System.out.println(System.getProperty("user.home"));
//       listAllFiles(new File(System.getProperty("user.home") + File.separator +"Documents"));
        copyFile("/tmp/11.txt", "/tmp/22.txt");
    }

    /**
     * @data: 2021/4/10-下午5:15
     * @User: zhaozhiwei
     * @method: copyFile
      * @param src :
 * @param dest :
     * @return: void
     * @Description: 文件复制
     */
    public static void copyFile(String src, String dest) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try{
            // 创建管道
            fileInputStream = new FileInputStream(src);
            fileOutputStream = new FileOutputStream(dest);

            // 搞个小桶放中间
            final byte[] buffer = new byte[20 * 1024];
            int cnt;

            while((cnt = fileInputStream.read(buffer, 0, buffer.length)) != -1){
                fileOutputStream.write(buffer, 0, cnt);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(!Objects.isNull(fileInputStream)){
                    fileOutputStream.close();
                }
                if(!Objects.isNull(fileInputStream)){
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @data: 2021/4/10-下午5:16
     * @User: zhaozhiwei
     * @method: listAllFiles
      * @param dir :
     * @return: void
     * @Description: 递归地列出一个目录下所有文件
     */
    private static void listAllFiles(File dir) {

        // 文件不存在返回空
        if(Objects.isNull(dir) || !dir.exists()){
            return;
        }

        if(dir.isFile()){
            System.out.println(dir.getName());
            return;
        }

        // 递归遍历文件
        for (File file : dir.listFiles()) {
            listAllFiles(file);
        }
    }
}
