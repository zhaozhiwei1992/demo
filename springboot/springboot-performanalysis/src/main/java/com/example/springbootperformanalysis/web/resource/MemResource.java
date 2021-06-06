package com.example.springbootperformanalysis.web.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryManagerMXBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootperformanalysis.web.resource
 * @Description: TODO
 * @date 2021/5/12 下午8:21
 */
@RestController
public class MemResource {

    /**
     * @data: 2021/5/19-下午2:39
     * @User: zhaozhiwei
     * @method: mem
     * @return: java.lang.Integer
     * @Description: 描述, 可以设置最大内存，快速内存溢出
     * -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath="/tmp/jvm_dump.hprof" -XX:-PrintGCDetails -Xms10M -Xmx10M
     * curl -X GET http://127.0.0.1:8080/mem
     * java.lang.OutOfMemoryError: Java heap space
     * at com.example.springbootperformanalysis.web.resource.MemResource.mem(MemResource.java:38) ~[classes/:na]
     * <p>
     * 通过visualvm, 或者 mat查看
     * 如果有jhat也可以
     * <p>
     * mat:
     * leak suspect
     * http-nio-8080-exec-1
     * at java.lang.OutOfMemoryError.<init>()V (OutOfMemoryError.java:48)
     * at com.example.springbootperformanalysis.web.resource.MemResource.mem()Ljava/lang/Integer; (MemResource
     * .java:39)
     * <p>
     * dominator_tree 显示 list下很多byte, 可以怀疑是list下塞入很多byte导致
     */
    @GetMapping("/mem")
    public Integer mem() {
        // 创建byte字节数组, 一个对象1m, 创建10000个
        final List<byte[]> bytes = new ArrayList<>();
        for (int i = 0; i < 2000; i++) {
            bytes.add(new byte[1024 * 1024]);
        }
        return bytes.size();
    }

    /**
     * @data: 2021/5/23-上午12:01
     * @User: zhaozhiwei
     * @method: info
     * @return: java.lang.String
     * @Description: 描述
     * curl -X GET http://127.0.0.1:8080/info
     */
    @GetMapping("/info")
    public String info() {
        final Runtime runtime = Runtime.getRuntime();

        // 这个玩意儿没有释放前，每次请求，free都会减少
        final byte[] bytes = new byte[1024 * 1024];
        final Map<String, Object> resultInfo = new HashMap<>();
        resultInfo.put("total", runtime.totalMemory() / (1024 * 1024) + "M");
        resultInfo.put("free", runtime.freeMemory() / (1024 * 1024) + "M");
        resultInfo.put("max", runtime.maxMemory() / (1024 * 1024) + "M");
        return resultInfo.toString();
    }

    @GetMapping("/mxBeans")
    public String mxBeans() {
        return ManagementFactory.getGarbageCollectorMXBeans().stream()
                .map(MemoryManagerMXBean::getName)
                .collect(Collectors.joining("\n"));
    }
}
