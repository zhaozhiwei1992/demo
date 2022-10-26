package com.example.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Title: MapController
 * @Package: com/example/springbootcache/controller/MapController.java
 * @Description: 示例2信息接口
 * @author: zhaozhiwei
 * @date: 2022/10/25 下午8:23
 * @version: V1.0
 */
@RestController
public class Example2Resource {

    private static final Logger log = LoggerFactory.getLogger(Example2Resource.class);

    /**
     * @date: 2022/10/25-上午10:19
     * @author: zhaozhiwei
     * @method: findByID
      * @param id : 唯一id
     * @return: com.lx.demo.springbootcache.domain.Map
     * @Description: 根据id获取示例2信息
     */
    @GetMapping("/examples2/{id}")
    public Map findByID(@PathVariable Long id){
        return null;
    }

    /**
     * @date: 2022/10/25-上午10:25
     * @author: zhaozhiwei
     * @method: deleteByID
      * @param id :
     * @return: com.lx.demo.springbootcache.domain.Map
     * @Description: 根据id删除示例2信息
     */
    @DeleteMapping("/examples2/{id}")
    public Map deleteByID(@PathVariable Long id){
        return null;
    }

    /**
     * @date: 2022/10/25-上午10:14
     * @author: zhaozhiwei
     * @method: save
      * @param 示例2 :
     * @return: com.lx.demo.springbootcache.domain.Map
     * @Description: 示例2保存方法
     */
    @PostMapping("/examples2")
    public Map save(@RequestBody Map 示例2){
        return null;
    }

    /**
     * @date: 2022/10/25-上午10:21
     * @author: zhaozhiwei
     * @method: update
      * @param 示例2 :
     * @return: com.lx.demo.springbootcache.domain.Map
     * @Description: 示例2修改内容
     */
    @PutMapping("/examples2")
    public Map update(@RequestBody Map 示例2){
        return null;
    }
}
