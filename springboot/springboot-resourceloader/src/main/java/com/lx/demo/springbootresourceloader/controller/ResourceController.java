package com.lx.demo.springbootresourceloader.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URL;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    @GetMapping("test")
    public String test(){
        // 启动main 方法 /home/lx7ly/workspace/demo/springboot/springboot-resourceloader/target/classes/myres
        // 使用jar包  file:/home/lx7ly/workspace/demo/springboot/springboot-resourceloader/target/springboot-resourceloader-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes!/myres
        final URL myres = Thread.currentThread().getContextClassLoader().getResource("myres");
        return myres.getPath();
    }

    /**
     * @data: 2023/3/8-上午9:06
     * @User: zhaozhiwei
     * @method: json

     * @return: java.lang.String
     * @Description: 读取json文件
     */
    @GetMapping("json")
    public String json() throws FileNotFoundException {
        String defaultTmpDir = System.getProperty("java.io.tmpdir");
        Resource resource = new ClassPathResource("json/anhui2020.json");

        FileReader fileReader = null;
        Reader reader = null;
        try {
            File jsonFile = new File(defaultTmpDir + "/anhui2020.json");
            FileUtils.copyToFile(resource.getInputStream(), jsonFile);
            fileReader = new FileReader(jsonFile);
            reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            String jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            //logger.error("读取文件报错", e);
            System.out.println("读取文件报错!"+e);
        } finally {
            if(fileReader != null){
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
