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
    public static void main(String[] args) throws IOException {
//        System.out.println(System.getProperty("user.home"));
//       listAllFiles(new File(System.getProperty("user.home") + File.separator +"Documents"));
//        copyFile("/tmp/11.txt", "/tmp/22.txt");
        createFile();
    }

    private static void createFile() throws IOException {
        String fileName = "test.txt";
        System.out.println("File.separator:" + File.separator);
        File testFile = new File("/tmp/test" + File.separator + fileName);
        //返回的是File类型,可以调用exsit()等方法
        File fileParent = testFile.getParentFile();
        //返回的是String类型
        String fileParentPath = testFile.getParent();
        System.out.println("fileParent:" + fileParent);
        System.out.println("fileParentPath:" + fileParentPath);
        if (!fileParent.exists()) {
            // 创建多级目录, 有目录才能创建文件
            fileParent.mkdirs();
        }
        if (!testFile.exists()) {
            //文件不存在时创建文件
            testFile.createNewFile();
        }
        System.out.println(testFile);

        String path = testFile.getPath();
        //得到文件/文件夹的绝对路径
        String absolutePath = testFile.getAbsolutePath();
        //得到文件/文件夹的名字
        String getFileName = testFile.getName();
        System.out.println("path:"+path);
        System.out.println("absolutePath:"+absolutePath);
        System.out.println("getFileName:"+getFileName);
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
