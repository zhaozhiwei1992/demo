package com.example.demo.web.resource;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class NacosController {

    private static final Logger logger = LoggerFactory.getLogger(NacosController.class);

    /**
     * 在nacos中配置键值对即可
     * 注: 格式必须是properties
     */
    @NacosValue(value = "${nacos.test.propertie:123}", autoRefreshed = true)
    private String testProperties;

    public void setTestProperties(String testProperties) {
        logger.info("before set {}", testProperties);
        this.testProperties = testProperties;
        logger.info("after set {}", testProperties);
    }

    /**
     * 测试nacos配置中心
     * @return
     */
    @GetMapping("/test")
    public String test(){
        return testProperties;
    }

    @NacosInjected
    private NamingService namingService;

    @Value("${spring.application.name}")
    private String providerApplicationName;

    /**
     * 模拟测试，直接使用同一个服务
     * @return
     * @throws NacosException
     */
    @GetMapping("/consumer/test")
    public String consumerTest() throws NacosException {
        // 根据服务名从注册中心获取一个健康的服务实例
        Instance instance = namingService.selectOneHealthyInstance(providerApplicationName);
        // 这里只是为了方便才新建RestTemplate实例
        RestTemplate template = new RestTemplate();
        String url = String.format("http://%s:%d/test", instance.getIp(), instance.getPort());
        String result = template.getForObject(url, String.class);
        System.out.println(String.format("请求URL:%s,响应结果:%s", url, result));
        return result;
    }
}
