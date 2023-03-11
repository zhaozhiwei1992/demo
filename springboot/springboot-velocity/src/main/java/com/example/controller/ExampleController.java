package com.example.controller;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Title: ExampleController
 * @Package com/example/controller/ExampleController.java
 * @Description: velocity模版渲染测试
 * @author zhaozhiwei
 * @date 2023/3/11 下午5:41
 * @version V1.0
 */
@Controller
public class ExampleController {

    private static final Logger logger = LoggerFactory.getLogger(ExampleController.class);

    /**
     * @data: 2023/3/11-下午6:07
     * @User: zhaozhiwei
     * @method: string
      * @param response :
     * @return: void
     * @Description: 浏览器访问http://ip:port/org-mode即可看到效果
     * 可以批量生成orgmode内容, 不用一个一个贴, 比如表结构文档
     */
    @RequestMapping("/org-mode")
    public void string(HttpServletResponse response) {
        // 生成一个orgmode文件
        // 测试velocity循环

        Map<String, Object> map = new HashMap<>();
        map.put("title", "测试velocity生成orgmod");
        map.put("items", Arrays.asList("* 标题1", "** 标题1.1", "* 标题2", "** 标题 2.1", "** 标题2.2"));

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        VelocityContext context = new VelocityContext(map);

        StringWriter sw = new StringWriter();
        Template tpl = Velocity.getTemplate("templates/Demo.orgmode.vm", "UTF-8");
        tpl.merge(context, sw);
        logger.info("生成orgmode文本 \n{}", sw);

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            // StringWriter写入流中
            IOUtils.write(sw.toString(), byteArrayOutputStream, "UTF-8");
            IOUtils.closeQuietly(sw);
            IOUtils.write(byteArrayOutputStream.toByteArray(), response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}