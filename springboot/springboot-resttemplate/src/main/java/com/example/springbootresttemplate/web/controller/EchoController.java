package com.example.springbootresttemplate.web.controller;

import com.example.springbootresttemplate.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 通过resttemplate访问该接口
 */
@RestController
@Slf4j
public class EchoController {

    @GetMapping("/echo/{name}")
    public String echo(@PathVariable String name){
        return "echo: "  + name;
    }

    /**
     * 2019-12-20 22:23:59.845  INFO 9667 --- [nio-8080-exec-8] c.e.s.web.controller.EchoController
     * : id = 1, user = User(id=1, name=zhangsan, age=18)
     * @param user
     * @param id
     * @return
     */
    @PostMapping("/echo/userid")
    public User echo(User user, int id){
        log.info("id = {}, user = {}", id, user);
        return user;
    }

    /**
     * 2019-12-20 22:38:15.040  INFO 9667 --- [nio-8080-exec-6] c.e.s.web.controller.EchoController      : name =
     * [zhangsan], user = User(id=null, name=lisi, age=18)
     * @param user
     * @param name
     * @return
     */
    @PostMapping("/echo/username")
    public User echo(@RequestBody User user, @RequestParam String name){
        log.info("name = {}, user = {}", name, user);
        return user;
    }

    /**
     * 请求带多参数，并且大数据量
     * curl -X POST -H 'Content-Type:application/json;charset=utf-8' http://127.0.0.1:8080/echo/mutiparam\?param1\=11.1,11.2,11.3\&param2\=xx,xx1 -d '[{}]'
     */
    @PostMapping("/echo/mutiparam")
    public List<Map> echo(@RequestBody List<Map> datas, @RequestParam String param1, @RequestParam String param2){
        log.debug("参数列表 datas:{}, param1:{}, param2:{}", datas, param1, param2);
        return datas;
    }

    /**
     * @data: 2021/2/4-上午11:38
     * @User: zhaozhiwei
     * @method: regexEcho
     * @param id :
     * @return: java.lang.String
     * @Description: id只能传入数字，但是提示不够优雅，应该使用@Valid注解进行参数校验
     */
    @GetMapping("/echo/regex/{id:[0-9]+}")
    public String regexEcho(@PathVariable String id){
        return "传入id: "  + id;
    }

    @PostMapping("/test/token")
    public String tokenid(@RequestBody HashMap paramMap, @RequestHeader HttpHeaders httpHeaders) throws IOException {
        final HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String tokenid = request.getParameter("tokenid");
        log.info("通过参数获取token {}", tokenid);
        tokenid = (String) request.getAttribute("tokenid");
        log.info("通过参数获取token {}", tokenid);

        //header获取
        final List<String> tokenid1 = httpHeaders.get("tokenid");
        log.info("通过header获取token {} ", tokenid1);
        log.info("通过request.header获取token {} ", request.getHeader("tokenid"));

        //body获取
        final Object tokenid2 = paramMap.get("tokenid");
        log.info("通过body获取tokenid {}", tokenid2);
//        final BufferedReader reader = request.getReader();
//        final String s = reader.readLine();
//        while (!StringUtils.isEmpty(s)){
//           log.info("通过body获取参数 {} ", s);
//        }
//        reader.close();

        return tokenid;
    }

    @GetMapping("/echo/amt/{amt}")
    public String echoAmtStr(@PathVariable String amt){
        return "echo: "  + amt;
    }

    @GetMapping("/echo/list/map")
    public String echoListMap(@RequestParam List<Map> params){
        // TODO 待测试
        return "echo: " + params;
    }
}
