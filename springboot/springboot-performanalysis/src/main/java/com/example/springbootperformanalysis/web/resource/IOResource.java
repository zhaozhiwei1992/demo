package com.example.springbootperformanalysis.web.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootperformanalysis.web.resource
 * @Description: TODO
 * @date 2021/5/14 上午9:33
 */
@RestController
public class IOResource {

    /**
     * @data: 2021/5/14-上午10:13
     * @User: zhaozhiwei
     * @method: io

     * @return: java.lang.String
     * @Description:
     * seq 1 10 |xargs -I{} curl -X GET http://127.0.0.1:8080/io
     * iostat
     * 十次请求, io写入瞬间飙升
     * Device             tps    kB_read/s    kB_wrtn/s    kB_dscd/s    kB_read    kB_wrtn    kB_dscd
        nvme0n1          98.00         0.00     16418.00         0.00          0      32836          0
        sda               0.00         0.00         0.00         0.00          0          0          0

     */
    @GetMapping("/io")
    public String io() throws IOException {

        // 初始化文件
        File file = new File("/tmp/test/test.txt");
        final File parentFile = file.getParentFile();
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }

        for (int i = 0; i < 100000; i++) {
            file = new File("/tmp/test/test_" + i + ".txt");
            file.deleteOnExit();
            file.createNewFile();
        }
        return "创建成功";
    }

    /**
     * @data: 2021/5/19-下午2:17
     * @User: zhaozhiwei
     * @method: io2

     * @return: java.lang.String
     * @Description: 描述
     * curl -X GET http://127.0.0.1:8080/io2
     */
    @GetMapping("/io2")
    public String io2() throws IOException {

        for (int i = 0; i < 100000; i++) {
            File file = new File("/tmp/test/test_" + i + ".txt");

//            没有增加管道前， 只new 上边file只是内存存在,不会校验机器是否存在目录
            try(final FileOutputStream fileOutputStream = new FileOutputStream(file);){
                for (int j = 51; j < 10000; j++) {
                    fileOutputStream.write(new byte[256]);
                }
                fileOutputStream.flush();
            }
        }
        return "创建成功";
    }
}
