//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.plugin;

import com.google.common.base.Optional;
import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spring.web.DescriptionResolver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Title: CustomOperationReader
 * @Package com/example/plugin/CustomOperationReader.java
 * @Description: 将注释信息转换为swagger描述信息
 * 下述类就是解析ApiOperation注解中value的地方
 * {@see springfox.documentation.swagger.readers.operation.OperationSummaryReader}
 * @author zhaozhiwei
 * @date 2022/10/25 下午9:57
 * @version V1.0
 */
@Component
@Order(0)
public class CommentOperationBuilder implements OperationBuilderPlugin {

    private final DescriptionResolver descriptions;

    @Value("${swagger.basePackage:com.example.web.rest}")
    private String basePackage;

    @Autowired
    public CommentOperationBuilder(DescriptionResolver descriptions) {
        this.descriptions = descriptions;
    }

    /**
     * @date: 2022/10/26-上午11:45
     * @author: zhaozhiwei
     * @method: apply
      * @param context :
     * @return: void
     * @Description: 根据完整方法签名，获取描述信息
     *
     * TODO md, 完整方法签名怎么搞?
     * 这里目前无法拿到类信息(可能太菜), 使用groupName, 如person resource，并且需要设置rest目录如com.example.web.rest
     * 从而来尽量精确匹配
     */
    public void apply(OperationContext context) {
        String operationName = context.getName();
        Optional<ApiOperation> apiOperationAnnotation = context.findAnnotation(ApiOperation.class);
        // 如果有这个注解, 并且设置了值就用这个
        if (apiOperationAnnotation.isPresent() && StringUtils.hasText(apiOperationAnnotation.get().value())) {
            context.operationBuilder().summary(this.descriptions.resolve(apiOperationAnnotation.get().value()));
        }else{
            // 通过自定义的方式给方法设置描述
            // 根据类+方法名获取方法注释信息(javadoc生成到某个文件中), 在这里填充
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
                // 1. 得到当前全类名, 直接获取不到(裂开),
                // 采用方式2, 从Environment中读取(com.example.web.rest),拼接groupName
                // json结构如: "com.example.web.rest.PersonResource.save(com.example.domain.Person)#Description":"保存方法"
                // person resource 转换为类名
                final String groupName = context.getGroupName();
                // 可能存在函数重载，需要将参数列表也写进去
                final StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(basePackage);
                stringBuilder.append(".");
                stringBuilder.append(toHump(groupName));
                stringBuilder.append(".");
                stringBuilder.append(operationName);
                // 拼接参数列表
                final List<ResolvedMethodParameter> parameters = context.getParameters();
                final List<String> paramTypeList =
                        parameters.stream().map(m -> m.getParameterType().toString()).collect(Collectors.toList());
                if(paramTypeList.size() > 0){
                    // 增加参数列表
                    final String join = String.join(",", paramTypeList);
                    stringBuilder.append("(");
                    stringBuilder.append(join);
                    stringBuilder.append(")");
                }else{
                    // 空参数
                    stringBuilder.append("()");
                }

                // 解析json文件, 获取类描述信息
                stringBuilder.append("#Description");
                final Gson gson = new Gson();
                final Map map = gson.fromJson(builder.toString(), HashMap.class);
                final Object o = map.get(stringBuilder.toString());
                if(!Objects.isNull(o)){
                    // 2. 获取描述信息
                    context.operationBuilder().summary(this.descriptions.resolve(o.toString()));
                }
            }
        }
    }

    /**
     * @date: 2022/10/26-下午4:00
     * @author: zhaozhiwei
     * @method: toHump
      * @param groupName :
     * @return: java.lang.String
     * @Description: 转换为驼峰标识
     *
     * person-resource --> PersonResource
     */
    private String toHump(String groupName) {
        final String[] split = groupName.split("-");
        final StringBuilder stringBuilder = new StringBuilder();
        for (String s : split) {
            // 首字母大写
            char[] chars = s.toCharArray();
            chars[0] = toUpperCase(chars[0]);
            stringBuilder.append(String.valueOf(chars));
        }
        return stringBuilder.toString();
    }

    /**
     * 字符转成大写
     *
     * @param c 需要转化的字符
     */
    public static char toUpperCase(char c) {
        if (97 <= c && c <= 122) {
            c ^= 32;
        }
        return c;
    }

    public boolean supports(DocumentationType delimiter) {
        return true;
    }
}
