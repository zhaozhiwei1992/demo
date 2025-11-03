package com.example.springbootresttemplate.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.springbootresttemplate.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/rest")
public class RestTemplateController {

    @Autowired
    private RestTemplate restTemplate;

    private String host = "http://127.0.0.1:8080";

    /**
     * forObject = echo: zhangsan
     * 2019-12-20 22:07:25.485  INFO 8748 --- [nio-8080-exec-1] c.e.s.w.c.RestTemplateController         :
     * headers: [Content-Type:"text/plain;charset=UTF-8", Content-Length:"14", Date:"Fri, 20 Dec 2019 14:07:25 GMT",
     * Keep-Alive:"timeout=60", Connection:"keep-alive"],
     * statuscode: 200,
     * body: echo: zhangsan
     * @param name
     * @return
     */
    @GetMapping("/echo/{name}")
    public String echo(@PathVariable String name){
        final String forObject = restTemplate.getForObject(host+"/echo/{name}", String.class, name);
        log.info("forObject = {}", forObject);
        final ResponseEntity<String> forEntity = restTemplate.getForEntity(host+"/echo/{name}", String.class, name);
        HttpHeaders headers = forEntity.getHeaders();
        HttpStatus statusCode = forEntity.getStatusCode();
        int code = statusCode.value();
        final String body = forEntity.getBody();
        log.info("headers: {}, statuscode: {}, body: {}", headers, code, body);
        return "echo: "  + name;
    }

    /**
     * Field error in object 'user' on field 'id': rejected value [1L]; codes [typeMismatch.user.id,typeMismatch.id,
     * typeMismatch.java.lang.Long,typeMismatch]; arguments [org.springframework.context.support
     * .DefaultMessageSourceResolvable: codes [user.id,id]; arguments []; default message [id]]; default message
     * [Failed to convert property value of type 'java.lang.String' to required type 'java.lang.Long' for property
     * 'id'; nested exception is java.lang.NumberFormatException: For input string: "1L"]]
     *
     * curl -X POST http://127.0.0.1:8080/rest/echo/userid?name=zhangsan&age=18&id=1
     * id信息会同时传递到user和id参数上
     * 2019-12-20 22:23:59.852  INFO 9667 --- [nio-8080-exec-7] c.e.s.w.c.RestTemplateController         : headers:
     * [Content-Type:"application/json", Transfer-Encoding:"chunked", Date:"Fri, 20 Dec 2019 14:23:59 GMT",
     * Keep-Alive:"timeout=60", Connection:"keep-alive"], statuscode: 200, body: User(id=1, name=zhangsan, age=18)
     *
     * @param user
     * @param id
     * @return
     */
    @PostMapping("/echo/userid")
    public User echo(User user, Long id){
        final MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("id", id);
        multiValueMap.add("name", user.getName());
        multiValueMap.add("age", user.getAge());
        //"Content type 'multipart/form-data;charset=UTF-8
        final ResponseEntity<User> forEntity = restTemplate.postForEntity(host + "/echo/userid", multiValueMap, User.class);
        HttpHeaders headers = forEntity.getHeaders();
        HttpStatus statusCode = forEntity.getStatusCode();
        int code = statusCode.value();
        final User body = forEntity.getBody();
        log.info("headers: {}, statuscode: {}, body: {}", headers, code, body);
        return user;
    }

    /**
     * curl -X POST http://127.0.0.1:8080/rest/echo/username?name=zhangsan -d {"name":"lisi","age":18} -H Content
     * -Type:application/json;charset=UTF-8
     *
     * 2019-12-20 22:38:15.042  INFO 9667 --- [nio-8080-exec-4] c.e.s.w.c.RestTemplateController         : headers:
     * [Content-Type:"application/json", Transfer-Encoding:"chunked", Date:"Fri, 20 Dec 2019 14:38:15 GMT",
     * Keep-Alive:"timeout=60", Connection:"keep-alive"], statuscode: 200, body: User(id=null, name=lisi, age=18)
     * @param user
     * @param name
     * @return
     */
    @PostMapping("/echo/username")
    public User echo(@RequestBody User user, @RequestParam String name){

//        final MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
//        multiValueMap.add("name", name);
//        final ResponseEntity<User> userResponseEntity = restTemplate.postForEntity(host + "/echo/username?name={name}", user,
//                User.class, multiValueMap);

        //方法1
        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", name);
//        final ResponseEntity<User> userResponseEntity = restTemplate.postForEntity(host + "/echo/username?name={name}", user,
//                User.class, hashMap);

        //方法2
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        //使用httpEntity的方式比较灵活，里面可以包装map, javabean,字符串等序列化对象
        final HttpEntity<User> userHttpEntity = new HttpEntity<>(user, httpHeaders);
        final ResponseEntity<User> userResponseEntity = restTemplate.postForEntity(host + "/echo/username?name={name}", userHttpEntity,
                User.class, hashMap);

        //java.lang.IllegalArgumentException: Not enough variable values available to expand 'name'
        //有请求体不能用下面方式请求
//        final ResponseEntity<User> userResponseEntity = restTemplate.postForEntity(host + "/echo/username?name={name}", hashMap,
//                User.class);
        HttpHeaders headers = userResponseEntity.getHeaders();
        HttpStatus statusCode = userResponseEntity.getStatusCode();
        int code = statusCode.value();
        final User body = userResponseEntity.getBody();
        log.info("headers: {}, statuscode: {}, body: {}", headers, code, body);
        return user;
    }

    /**
     * @data: 2021/12/29-上午9:20
     * @User: zhaozhiwei
     * @method: testTokenid
     * @return: void
     * @Description: 测试resttemplate传输 tokenid, 统一post测试
     */
    @GetMapping("/echo/token")
    public void testTokenid(){

        String tokenid = "";
        JSONObject objectHashMap = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/json; charset=UTF-8"));
        HttpEntity<String> formEntity = new HttpEntity<>(objectHashMap.toString(), headers);
        String url = host + "/test/token";

        //1. url传参
        tokenid = "fromurl111";
        String result = restTemplate.postForObject(url + "?tokenid="+tokenid, formEntity, String.class);
        log.info("传入token {}, 返回结果 {}", tokenid, result);

        //2. header传输
        tokenid = "fromheader222";
        headers.set("tokenid", tokenid);
        result = restTemplate.postForObject(url, formEntity, String.class);
        log.info("传入token {}, 返回结果 {}", tokenid, result);

        //3. requestbody传输
        tokenid = "frombody333";
        objectHashMap.put("tokenid", tokenid);
        log.info("请求项目库url {}, 参数: {}", url, formEntity);
        formEntity = new HttpEntity<>(objectHashMap.toString(), headers);
        result = restTemplate.postForObject(url, formEntity, String.class);
        log.info("传入token {}, 返回结果 {}", tokenid, result);
    }

    @GetMapping("/echo/menu")
    public void testMenu(){

        String ifmisPortalBaseAddress = "192.168.100.222:8001";
        String tokenid = "B4E9C32BADB81AC1E0530100007F5914oAHhlNQX";
        Map<String, String> userinfo = new HashMap<>();
        userinfo.put("year", "2024");
        userinfo.put("province", "340000000");

        String url;
        //  curl -X POST http://192.168.100.222:8001/buscommon/llm/queryPermission?tokenid=B4E9C32BADB81AC1E0530100007F5914oAHhlNQX&fiscal_year=2024&mof_div_code=340000000 -H "Content-Type:application/json;charset=UTF-8" -H "Accept:application/json;charset=UTF-8"  -d '["menuid_000","menuid_001","menuid_002","menuid_003"]'
        url = String.format("http://%s/buscommon/llm/queryPermission?tokenid=%s&fiscal_year=%s&mof_div_code=%s"
                , ifmisPortalBaseAddress, tokenid, userinfo.get("year"), userinfo.get("province"));
        log.info("获取菜单权限请求地址：{}", url);
        try {
            List<String> menuList = new ArrayList<>();
            menuList.add("123654");
            ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url, menuList, Object.class);
            if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                log.info("一体化服务端返回数据：{}", responseEntity.getBody());
                Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();
                // 安全处理 data 字段：可能是 List 或 Map
                Object dataObj = responseBody.get("data");
                List<Map<String, Object>> dataList = new ArrayList<>();

                if (dataObj instanceof List) {
                    // 如果 data 是数组，直接强转为 List
                    dataList = (List<Map<String, Object>>) dataObj;
                } else if (dataObj instanceof Map) {
                    // 如果 data 是单个对象，包装为单元素 List
                    dataList.add((Map<String, Object>) dataObj);
                } else {
                    // 处理其他意外类型（例如 null 或未知类型）
                    throw new RuntimeException("data字段类型不预期: " + (dataObj != null ? dataObj.getClass() : "null"));
                }

                Map<String, Object> m2 = dataList.get(0);
                System.out.println(m2);
            }
        } catch (Exception e) {
            throw new RuntimeException("一体化服务端获取菜单权限信息失败", e);
        }
    }

}
