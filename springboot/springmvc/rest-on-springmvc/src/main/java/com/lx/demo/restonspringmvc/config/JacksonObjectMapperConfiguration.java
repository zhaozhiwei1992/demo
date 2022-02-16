package com.lx.demo.restonspringmvc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
@ConditionalOnClass({Jackson2ObjectMapperBuilder.class})
public class JacksonObjectMapperConfiguration {

    /**
     * @data: 2022/2/11-下午2:15
     * @User: zhaozhiwei
     * @method: jacksonObjectMapper
      * @param builder :
     * @return: com.fasterxml.jackson.databind.ObjectMapper
     * @Description:
     *  {@see org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration}
     *  {@see com.lx.demo.restonspringmvc.controller.MutiParamController#mutiParam2(com.lx.demo.restonspringmvc.model.Person)}
     *  {@see com.lx.demo.restonspringmvc.JacksonTest}
     *          mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
     *
     *  curl -X POST http://127.0.0.1:8080/mutiparam2 -H 'Content-Type:application/json;charset=utf8' -d '{"ixxd":1,"age":18,"name":"goudan"}'
     *  使用上述方式，请求json中增加person不存在的field信息, 出现下述报错
     *
     * {"timestamp":1644560193441,"status":400,"error":"Bad Request","message":"JSON parse error: Unrecognized field \"ixxd\"
     * (class com.lx.demo.restonspringmvc.model.Person),
     * not marked as ignorable; nested exception is com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException: Unrecognized field \"ixxd\"
     * (class com.lx.demo.restonspringmvc.model.Person),
     * not marked as ignorable (3 known properties: \"id\", \"age\", \"name\"])\n
     * at [Source: (PushbackInputStream); line: 1, column: 10] (through reference chain:
     * com.lx.demo.restonspringmvc.model.Person[\"ixxd\"])","path":"/mutiparam2"}
     *  原因: 可能有人定义了ObjectMapper, 并且定义的方式比较沙b, 如rocketmq-springboot-starter
     *
     */
//    @Bean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
//        return builder.createXmlMapper(false).build();
        return new ObjectMapper();
    }
}
