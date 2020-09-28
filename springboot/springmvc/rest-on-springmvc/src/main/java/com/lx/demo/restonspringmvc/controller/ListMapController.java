package com.lx.demo.restonspringmvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Description 大佬写点东西吧
 * @auther lx7ly
 * @create 2019-10-17 下午8:04
 */
@RestController
public class ListMapController {

    private static final Logger logger = LoggerFactory.getLogger(ListMapController.class);

    /**
     * 插入数据测试
     * curl -X POST -H "Content-Type:application/json;charset=UTF-8"
     * http://192.168.2.33:8080/insert\?province\=4300 -d '[{"name":"zhangsan"},{"name":"lisi"}]'
     *
     * 这里requestbody是必须的，否则会出现nosuchmethod
     * java.lang.NoSuchMethodException: java.util.List.<init>()
     * @param province
     * @param data
     * @return
     */
    @PostMapping("/insert")
    public String insert(@RequestParam String province, @RequestBody List<? extends Map> data){
        logger.info("province {}", province);
        logger.info("data {}", data);
        return "";
    }

    /**
     * curl -X POST -H "Content-type:application/json;charset=utf-8" http://127.0.0.1:8080/confirmAssign\?guidList\=11,12,13 -d "xx"
     * @param guidList
     * @param param
     * @return
     */
    @PostMapping(value = "/confirmAssign", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List confirmAssign(@RequestParam List guidList, @RequestBody String param) {
        logger.info("{}", guidList);
        final String xx = parseInSql("xx", guidList);
        System.out.println(xx);
        return guidList;
    }

    /**
     * 将过长的in语句解析成or语句
     * @return or和in语句
     */
    public String parseInSql(String colcode, Collection<String> guids) {
        StringBuilder vchsql = new StringBuilder();
        vchsql.append(colcode).append(" in( ");
        int index = 0;
        Iterator iterator = guids.iterator();
        for (; iterator.hasNext();) {
            index++;
            vchsql.append("'").append(iterator.next()).append("'");
            if (index == 998) {
                index = 0;
                vchsql.append(") or ").append(colcode).append(" in (");
            }
            else {
                if (iterator.hasNext()) {
                    vchsql.append(",");
                }
            }
        }
        return vchsql.append(")").toString();
    }


}
