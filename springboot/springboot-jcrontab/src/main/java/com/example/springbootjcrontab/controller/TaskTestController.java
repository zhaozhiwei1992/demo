package com.example.springbootjcrontab.controller;

import org.jcrontab.Crontab;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class TaskTestController {

    private static Crontab crontab = Crontab.getInstance();

    /**
     * 定时任务测试,
     * curl -s --request GET \
     * -H 'cache-control: no-cache' \
     * -o tmp \
     * --url "http://127.0.0.1:8080/bdg/executetask?appid=bdg&tokenid=${token}&beanstr=bdg.timertask
     * .TianJinPlanTask"
     *
     * @param request
     * @param response
     * @param config
     * @return
     */
    @GetMapping("/bdg/executetask")
    public Map executeTask(HttpServletRequest request, HttpServletResponse response, Map config) {
//        try {
//            BdgCommonTask.execute(new String[]{"1", "2", request.getParameter("beanstr")});
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }

        crontab.onceTask("com.example.springbootjcrontab.business.BdgCommonTask", "execute", new String[]{"1", "2", request.getParameter("beanstr")});
        return config;
    }

}