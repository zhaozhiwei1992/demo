package com.example.config;

import feign.Contract;
import org.springframework.context.annotation.Bean;

/**
 * 不写configuration注解，就不会被默认扫描
 */
public class FeignConfiguration {

    /**
     * feign默认配置类
     * {@see org.springframework.cloud.netflix.feign.FeignClientsConfiguration}
     * 契约改为feign的原生契约， feign默认用springmvc的契约
     *     @Bean
     *     @ConditionalOnMissingBean
     *     public Contract feignContract(ConversionService feignConversionService) {
     *         return new SpringMvcContract(this.parameterProcessors, feignConversionService);
     *     }
     * @return
     */
    @Bean
    public Contract feignContract(){
        return new feign.Contract.Default();
    }
}
