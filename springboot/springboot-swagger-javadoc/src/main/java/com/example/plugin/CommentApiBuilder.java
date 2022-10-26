package com.example.plugin;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.gson.Gson;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.service.ResourceGroup;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ApiListingBuilderPlugin;
import springfox.documentation.spi.service.contexts.ApiListingContext;
import springfox.documentation.spring.web.paths.Paths;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: CustomApiBuilder
 * @Package com/example/plugin/CustomApiBuilder.java
 * @Description: 通过注释初始化类描述信息
 * 这里可以根据类名，设置类的描述信息, 描述信息需要在maven编译的时候放到某个文件中
 * 替换@Api
 * {@see SwaggerApiListingReader}
 * @date 2022/10/25 下午9:32
 */
@Component
@Order(0)
public class CommentApiBuilder implements ApiListingBuilderPlugin {
    @Override
    public void apply(ApiListingContext apiListingContext) {
        ResourceGroup group = apiListingContext.getResourceGroup();
        String description = group.getControllerClass().transform(this.description()).or(group.getGroupName());
        // 获取class根目录
        final String path =
                Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
        String content;
        StringBuilder builder = new StringBuilder();

        File file = new File(path + File.separator + "javadoc.json");
        InputStreamReader streamReader = null;
        try {
            streamReader = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            while ((content = bufferedReader.readLine()) != null)
                builder.append(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (builder.length() > 0) {
            // 解析json文件, 获取类描述信息
            final Gson gson = new Gson();
            final Map map = gson.fromJson(builder.toString(), HashMap.class);
            // 1. 得到当前全类名
            final Optional<? extends Class<?>> controllerClassOptional = group.getControllerClass();
            if(controllerClassOptional.isPresent()){
                final String name = controllerClassOptional.get().getName();
                // 2. 获取描述信息
                final Object o = map.get(name + "#Description");
                if(!Objects.isNull(o)){
                    description = o.toString();
                }
            }
        }
        apiListingContext.apiListingBuilder().description(description);
    }

    private Function<Class<?>, String> description() {
        return new Function<Class<?>, String>() {
            public String apply(Class<?> input) {
                return Paths.splitCamelCase(input.getSimpleName(), " ");
            }
        };
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }
}