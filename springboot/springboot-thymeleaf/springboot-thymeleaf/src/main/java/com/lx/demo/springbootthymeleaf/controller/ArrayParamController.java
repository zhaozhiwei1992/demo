package com.lx.demo.springbootthymeleaf.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/arrays")
@Controller
public class ArrayParamController {

    private static final Logger logger = LoggerFactory.getLogger(ArrayParamController.class);

    /**
     * requestbody可以解析前台传入的json信息
     *
     data : JSON.stringify(ids), 这里要将json字符串解析成数组
     如果不加requestbody, 这里方法都不会进入

     通过浏览器查看请求参数
     requestpayload
     ["1","2"]
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping(value = "/delete1")
    @ResponseBody
    public String delete(@RequestBody String[] ids, HttpServletRequest request) {
        final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info("传入数组信息 {} {}", methodName, ids);
        return "";
    }

    /**
     * 如果前端不做处理，后端传入数组参数需要特殊处理
     * 前端不增加traditional:true,
     *
     * requestpayload
     * ids%5B%5D=1&ids%5B%5D=2
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping(value = "/delete2")
    @ResponseBody
    public String delete2(String[] ids, HttpServletRequest request) {
        final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info("传入数组信息 {} {}", methodName, ids);

        if(ids == null){
            String[] array = request.getParameterValues("ids[]");
            for (String string : array) {
                System.out.println(string);
            }
        }
        return "";
    }

    /**
     * 通过payload就可以知道为啥后端可以解析或者解析不了了
     * requestpayload
     * ids=1&ids=2
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping(value = "/delete3")
    @ResponseBody
    public String delete3(String[] ids, HttpServletRequest request) {
        final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info("传入数组信息 {} {}", methodName, ids);
        return "";
    }

}
