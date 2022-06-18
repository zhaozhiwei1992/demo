package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Title: SwaggerConfiguration
 * @Package com/example/config/SwaggerConfiguration.java
 * @Description:
 * 引入该依赖包后, 服务启动可以通过 http://localhost:9090/swagger-ui.html来查看swagger配置
 * @author zhaozhiwei
 * @date 2022/6/18 下午3:44
 * @version V1.0
 */
@EnableSwagger2
@Configuration
public class SwaggerConfiguration {
    @Value("${swagger.basePackage:com.example.web}")
    private String basePackage;
    @Value("${swagger.title:微服务接口测试}")
    private String title;
    @Value("${swagger.description:外部服务接口API}")
    private String description;

    @Value("${swagger.version:V3.2.9}")
    private String version;

    /**
     * Springfox 提供了一个 Docket 对象，让我们可以灵活的配置 Swagger 的各项属性
     * http://localhost:8080/v2/api-docs
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //　自定义响应消息
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, Arrays.asList(
                        new ResponseMessageBuilder()
                                .code(500)
                                .message("服务器发生异常")
                                .responseModel(new ModelRef("Error"))
                                .build(),
                        new ResponseMessageBuilder()
                                .code(403)
                                .message("资源不可用")
                                .build()
                ))
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(version)
                .build();
    }
}
