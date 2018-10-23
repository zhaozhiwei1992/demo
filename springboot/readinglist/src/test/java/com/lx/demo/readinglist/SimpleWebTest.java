package com.lx.demo.readinglist;

import org.aspectj.bridge.MessageUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;

/**
 * @SpringApplicationConfiguration(classes = arrayOf(BootApplication::class))
 * @WebIntegrationTest("server.port=8081")
 *
 * in Spring Boot 1.5+ are equivalent to
 * @SpringBootTest(classes = arrayOf(BootApplication::class), properties = arrayOf("server.port=8081"))
 * @WebAppConfiguration
 *
 * https://stackoverflow.com/questions/44567632/which-version-of-spring-boot-has-webintegrationtest-included-it-shows-red-in-i
 *
 * https://juejin.im/post/5b95dbe46fb9a05cd7772503 //2.0以后测试写法
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimpleWebTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void pageNotFound() {
        try {
            String object = testRestTemplate.getForObject(
                    "/bogusPage", String.class);
            MessageUtil.fail("Should result in HTTP 404");
        } catch (HttpClientErrorException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
            throw e;
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
