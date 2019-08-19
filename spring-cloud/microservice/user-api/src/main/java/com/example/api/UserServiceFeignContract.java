package com.example.api;

import com.example.domain.User;
import feign.Param;
import feign.RequestLine;

/**
 * 使用feign原生契约的客户端, 一但指定configuration后对使用springmvc 契约的客户端造成影响不能启动，所以不能混用
 * 或者网上有混合配置方案，待确定//todo
 */
//@FeignClient(name = "${service.provider.name}")
//@FeignClient(name = "${service.provider.name}", configuration = FeignConfiguration.class)
public interface UserServiceFeignContract {

    /**
     * 使用feign自带注解 requestline
     * {@see https://github.com/OpenFeign/feign}
     * @param id
     * @return
     */
    @RequestLine("GET /{id}")
    User findById(@Param("id") Long id);
}
