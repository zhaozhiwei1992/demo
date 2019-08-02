package com.lx.demo.exceptiononspringmvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class IndexController {

    // @PostMapping    // Post 请求   @RequestMapping(method = RequestMethod.POST)  Create(C)
    // @GetMapping     // GET 请求    @RequestMapping(method = RequestMethod.GET) Read(R)
    // @PutMapping    // Put 请求的   @RequestMapping(method = RequestMethod.PUT) Update(U)
    // @DeleteMapping // Post 请求    @RequestMapping(method = RequestMethod.DELETE) Delete(D)
    // Windows 通过 PostMan 来测试
    // curl -X POST
    @RequestMapping("/hello")
    public String hello(){
        String msg = "hello world";
        return msg;
    }

    @RequestMapping("/hello/{name}")
    public String hello(@PathVariable("name") String name){
        String msg = "hello " + name;
        return msg;
    }

    @RequestMapping("/npe")
    public String npe(){
        throw new NullPointerException("故意抛异常！");
    }

    /**
     * 匹配 / 或者 ""
     * @return
     */
//    @GetMapping
//    public String index(){
//        return "index" ;
//    }
//"javax.servlet.error.status_code"
    /**
     * 处理页面找不到的情况
     * @param status
     * @param request
     * @param throwable
     * @return
     */
    @GetMapping("/404.html")
    public Map<String,Object> handlePageNotFound(HttpStatus status,
                                                 HttpServletRequest request,
                                                 Throwable throwable) {

        Map<String,Object> errors = new HashMap<>();

        errors.put("statusCode",
                request.getAttribute("javax.servlet.error.status_code"));
        errors.put("requestUri",
                request.getAttribute("javax.servlet.error.request_uri"));

        return errors;
    }

    //捕获所有异常
//    @ExceptionHandler(Exception.class)

    //局部方式异常捕获优先级最高
    //精确指定
    @ExceptionHandler(value = {
//            NullPointerException.class,
            IllegalAccessException.class,
            IllegalStateException.class,
    })
    @ResponseBody
    public void exceptionHandler(Exception e) {
        log.error("[{}] system error", e);
    }
}
