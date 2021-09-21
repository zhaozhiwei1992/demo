package com.lx.demo.springbootlog.web.rest;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: ChangeLoggerController
 * @Package com/lx/demo/springbootlog/web/rest/ChangeLoggerController.java
 * @Description: loggerback程序变更日志级别, 注意必须引入logback的level和logger
 * import ch.qos.logback.classic.Level;
 * import ch.qos.logback.classic.Logger;
 * @date 2021/9/21 下午9:36
 */
@RestController
public class ChangeLogbackLevelController {

    /**
     * @data: 2021/9/21-下午9:53
     * @User: zhaozhiwei
     * @method: change
     * @param classStr 需要修改的包名或类
     * @param levelStr 日志级别字符串标识
     * @return: java.lang.String
     * @Description:
     * 1. 修改日志级别
     *curl -X POST http://127.0.0.1:8080/logger/change\?classStr\=com.lx.demo.springbootlog.web.rest.IndexResource\&levelStr\=INFO
     *  -H "Content-Type=application/json;charset=utf-8"
     *
     * 2. 获取现在日志级别:
     *   curl -X GET http://127.0.0.1:8080/actuator/loggers/com.lx.demo.springbootlog.web.rest.IndexResource
     */
    @PostMapping("/logger/change")
    public String change(String classStr, String levelStr) {
        try {
            Level level = Level.toLevel(levelStr.toUpperCase());
            Logger restClientLogger = (Logger) LoggerFactory.getLogger(classStr);
            restClientLogger.setLevel(level);
        } catch (Exception e) {
            return "失败";
        }
        return "成功";
    }


    /**
     * 修改全局日志级别，但是我测试的时候只修改了第三方jar的日志级别，我的项目包的日志级别没有修改成功
     *
     * @param l
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/logger/changeRoot", method = RequestMethod.GET, produces = "application/json")
    public String change(String l) {
        try {
            Level level = Level.toLevel(l);
            Logger root = (Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
            root.setLevel(level);
        } catch (Exception e) {
            return "失败";
        }
        return "成功";
    }

    /**
     * @data: 2021/9/21-下午9:46
     * @User: zhaozhiwei
     * @method: index
      * @param request :
     * @param response :
     * @return: void
     * @Description: 描述
     * 查看目前服务的所有日志级别信息, 类似actuator/loggers
     */
    @RequestMapping(value = "/loggers", method = RequestMethod.GET)
    public void index(HttpServletRequest request, HttpServletResponse response) {

        StringBuilder sb = new StringBuilder();
        try {
            sb.append("<html>");
            Writer writer = response.getWriter();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=utf-8");
            LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
            lc.getLoggerList().forEach(logger -> {
                sb.append("<span style='display:block;'>");
                sb.append(logger.getName()).append(",").append(logger.getEffectiveLevel());
                sb.append("</span>");
            });
            sb.append("</html>");
            writer.write(sb.toString());
            writer.flush();
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
        }

    }

}