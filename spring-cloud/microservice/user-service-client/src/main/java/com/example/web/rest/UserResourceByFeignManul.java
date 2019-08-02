package com.example.web.rest;

import com.example.api.UserService;
import com.example.domain.User;
import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.cloud.netflix.feign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 注意: 导入feign默认配置, 这样才会去初始化feign的bean,比如decode, encoder等
 * 否则这里启动服务报错，找不到bean
 */
@Import(FeignClientsConfiguration.class)
@RestController
public class UserResourceByFeignManul{

    /**
     * 不加入注解，手工创建feign客户端
     * 为了避免冲突　关闭启动类中@EnableFeignClients, 并且去掉userservice上的feignclient注解,实际测试感觉不去掉也没有毛的影响
     */
    private UserService userFeign;

    private UserService adminFeign;

    /**
     * 构造器注入feign提供的一些实体
     * @param decoder
     * @param encoder
     * @param client
     * @param contract
     */
    public UserResourceByFeignManul(Decoder decoder, Encoder encoder, Client client, Contract contract) {
        this.userFeign = Feign.builder()
                .client(client)
                .encoder(encoder)
                .decoder(decoder)
                .contract(contract)
                .requestInterceptor(new BasicAuthRequestInterceptor("user", "password1"))
                .target(UserService.class, "http://user-service-provider/");

        this.adminFeign = Feign.builder()
                .client(client)
                .encoder(encoder)
                .decoder(decoder)
                .contract(contract)
                .requestInterceptor(new BasicAuthRequestInterceptor("admin", "password2"))
                .target(UserService.class, "http://user-service-provider/");
    }

    @GetMapping("/user-feign/{id}")
    User findByIDByUserFeign(@PathVariable("id") Long id){
        final User user = new User();
        user.setId(0L);
        user.setName("");
        user.setAge(0);

        userFeign.createUser(user);
        return user;
    }

    /**
     * spring 解析encoder解析不了{} 好神奇 //todo 这个后续还得研究下
     * 报错:
     * {"timestamp":1564754772545,"status":500,"error":"Internal Server Error","exception":"java.lang.IllegalArgumentException","message":"Illegal character in path at index 35: http://user-service-provider/users/{id}","path":"/admin-feign/1"}%
     * @param id
     * @return
     */
    @GetMapping("/admin-feign/{id}")
    User findByIDByAdminFeign(Long id){
        return adminFeign.findById(id);
    }
}
