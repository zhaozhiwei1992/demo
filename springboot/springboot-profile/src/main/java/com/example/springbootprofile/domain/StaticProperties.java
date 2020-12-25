package com.example.springbootprofile.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootprofile.domain
 * @Description: 初始化static变量
 * 这里要特别注意，自动生成的getter和setter方法，会带有static的限定符，需要去掉，才可以。
 * @date 2020/12/25 上午10:31
 */
@Component
public class StaticProperties {

    private static String staticProp;

    public String getStaticProp() {
        return staticProp;
    }

    @Value("${mysql.db:test}")
    public void setStaticProp(String staticProp) {
        StaticProperties.staticProp = staticProp;
    }

    /**
     * @data: 2020/12/25-上午10:52
     * @User: zhaozhiwei
     * @method: init
     * @return: void
     * @Description: @PostConstruct 注解的方法在加载类的构造函数之后执行，也就是在加载了构造函数之后，执行init方法, 会覆盖上面test值
     */
    @PostConstruct
    public void init() {
        staticProp = "postconstruct赋值";
    }
}
