package com.lx.demo.springbootconditional.config;

import com.lx.demo.springbootconditional.condition.LinuxCondition;
import com.lx.demo.springbootconditional.condition.WindowsCondition;
import com.lx.demo.springbootconditional.service.LinuxService;
import com.lx.demo.springbootconditional.service.OSService;
import com.lx.demo.springbootconditional.service.WindowsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * 通过条件化配置初始化bean
 */
@Configuration
public class OSServiceConfiguration {

    /**
     * 这里注意一定要加入@bean注解
     * @return
     */
    @Bean
    @Conditional(LinuxCondition.class)
    public OSService linuxService(){
        return new LinuxService();
    }

    @Bean
    @Conditional(WindowsCondition.class)
    public OSService windowsService(){
        return new WindowsService();
    }
}
