package com.example.aop;

import com.example.domain.DefaultJump;
import com.example.domain.Jump;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.aop
 * @Description:
 * 借助于AOP的引入功能，我们可以不必在设计上妥协
 * 或者侵入性地改变现有的实现
 * @date 2022/2/20 上午12:03
 */
@Aspect
@Component
public class JumpIntroducer {

    /**
     * value属性指定了哪种类型的bean要引入该接口
     * defaultImpl属性指定了为引入功能提供实现的类。在这里，
     * 我们指定的是DefaultEncoreable提供实现。
     * @DeclareParents注解所标注的静态属性指明了要引入了接口。
     */
    @DeclareParents(value = "com.example.domain.Run+", defaultImpl = DefaultJump.class)
    private Jump jump;
}
