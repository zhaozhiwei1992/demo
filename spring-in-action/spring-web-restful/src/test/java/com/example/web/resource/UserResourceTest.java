package com.example.web.resource;

import com.example.domain.User;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: JUnit3 Test Class.java.java
 * @Package com.example.web.resource
 * @Description:
 *
 * 手动执行测试, 需要先启动web项目
 *
 * @date 2022/3/7 下午2:34
 */
public class UserResourceTest extends TestCase {

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testUsers() {
        final List<User> list = restTemplate.getForObject("http://127.0.0.1:8080/users", List.class);
        Assert.notEmpty(list, "缺少用户信息");
    }

    @Test
    public void testFindUserById() {
        final ResponseEntity<User> userResponseEntity = restTemplate.getForEntity("http://127.0.0.1:8080/user/1",
                User.class);
        Assert.isTrue(HttpStatus.OK.equals(userResponseEntity.getStatusCode()), "获取数据失败");
    }

    @Test
    public void testSaveUser() {
        final User zhangsan = new User(1, "zhangsan");
        final ResponseEntity<User> responseEntity = restTemplate.postForEntity("http://127.0.0.1:8080/user",
                zhangsan, User.class);
        Assert.isTrue(HttpStatus.CREATED.equals(responseEntity.getStatusCode()), "保存失败");
//        System.out.println(responseEntity.getHeaders().getLocation());
    }
}