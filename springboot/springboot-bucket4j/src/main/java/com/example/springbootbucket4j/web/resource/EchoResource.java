package com.example.springbootbucket4j.web.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootbucket4j.web.resource
 * @Description: TODO
 * @date 2022/8/18 上午10:12
 */
@RestController
public class EchoResource {

    /**
     * @data: 2022/8/18-上午11:23
     * @User: zhaozhiwei
     * @method: echo
      * @param msg :
     * @return: java.lang.String
     * @Description: 描述
     * seq 20 |xargs -i echo "http://127.0.0.1:8080/echo/\?username\=zhangsan\&msg\=xx" |xargs -n 1 curl -X GET
     *
     * com.example.springbootbucket4j.aop.Bucket4jFilter#OVERDRAFT 这个值会直接影响到请求的个数
     *
     * 上述请求表示username是zhangsan的用户请求20次, 如果单位时间内超过20次，就会触发拦截
     * com.example.springbootbucket4j.aop.Bucket4jFilter#doFilterInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
     */
    @GetMapping("/echo")
    public String echo(@RequestParam String msg){
        return "echo: " + msg + "\n";
    }
}
