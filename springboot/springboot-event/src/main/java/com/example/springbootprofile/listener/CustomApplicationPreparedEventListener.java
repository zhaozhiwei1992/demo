package com.example.springbootprofile.listener;

import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Title: CustomApplicationPreparedEventListener
 * @Package com/example/springbootprofile/listener/CustomApplicationPreparedEventListener.java
 * @Description:
 * ApplicationPreparedEvent:spring boot上下文context创建完成，但此时spring中的bean是没有完全加载完成的。
 * 在获取完上下文后，可以将上下文传递出去做一些额外的操作。值得注意的是：在该监听器中是无法获取自定义bean并进行操作的。
 * @author zhaozhiwei
 * @date 2023/4/13 下午3:51
 * @version V1.0
 */
public class CustomApplicationPreparedEventListener implements ApplicationListener<ApplicationPreparedEvent> {
    @Override
    public void onApplicationEvent(ApplicationPreparedEvent event) {
        ConfigurableApplicationContext context = event.getApplicationContext();
        passContextInfo(context);
    }

    /**
     * 传递上下文
     */
    private void passContextInfo(ApplicationContext applicationContext) {
        //dosomething()
    }
}