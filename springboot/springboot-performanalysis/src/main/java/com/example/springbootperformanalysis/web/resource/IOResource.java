package com.example.springbootperformanalysis.web.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
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
}
