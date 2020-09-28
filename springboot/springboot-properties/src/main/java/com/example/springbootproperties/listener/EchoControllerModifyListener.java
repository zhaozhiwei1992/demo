package com.example.springbootproperties.listener;

import com.example.springbootproperties.controller.EchoController;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * 定义一个应用事件，监听应用上下文刷新事件，然后我们可以通过这个事件拿到ApplicationContext上下文，进而去获取对应的SpringBean，然后进行反射赋值操作。
 * 有了这个监听事件，我们需要把这个监听器添加到Spring的监听器集合中，可以通过启动类的启动方法进行添加：
 */
public class EchoControllerModifyListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Object echoController = event.getApplicationContext().getBean(EchoController.class);
        if (echoController instanceof EchoController) {
            try {
                Field field = EchoController.class.getDeclaredField("msg");
                field.setAccessible(true);
                ReflectionUtils.setField(field, echoController, "updateBy_com.example.springbootproperties.listener.EchoControllerModifyListener");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }
}