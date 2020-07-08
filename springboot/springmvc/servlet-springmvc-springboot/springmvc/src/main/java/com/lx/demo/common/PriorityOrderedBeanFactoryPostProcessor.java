package com.lx.demo.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;

/**
 * 编程方式设置文件编码
 */
@Component
public class PriorityOrderedBeanFactoryPostProcessor implements BeanFactoryPostProcessor,
        PriorityOrdered {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
            throws BeansException {
        Map map = beanFactory.getBeansOfType(PropertySourcesPlaceholderConfigurer.class);
        // 可能存在多个PropertySourcesPlaceholderConfigurerbean的情况
        Iterator<Map.Entry<String, PropertySourcesPlaceholderConfigurer>> it = map.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, PropertySourcesPlaceholderConfigurer> entry = it.next();
            PropertySourcesPlaceholderConfigurer pp = entry.getValue();
            pp.setFileEncoding("UTF-8");
            // 终于获取到了设置文件编码类型的地方了
            // 这也是最关键的地方
        }
    }

    @Override
    public int getOrder() {
        // 排序规则，具体可以看看OrderComparator的排序规则
        // PropertySourcesPlaceholderConfigurer的排序order是最低的，所以设置一个0完全可以满足要求
        return 0;
    }
}