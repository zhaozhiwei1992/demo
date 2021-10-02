package com.lx.demo.springbootjdbc;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.lx.demo.springbootjdbc.domain.User;
import org.slf4j.LoggerFactory;

/**
 * @Title: MapperPropertyTestDemo
 * @Package com/lx/demo/springbootjdbc/MapperPropertyTestDemo.java
 * @Description:
 * 测试不同属性复制性能差异
 * mappingBySpringBeanUtils
 * mappingByCglibBeanCopier
 * mappingByApacheBeanUtils
 * mappingByApachePropertyUtils
 *
 * mappingBySpringBeanUtils 请求次数: 1000 cost: 123
 * mappingByCglibBeanCopier 请求次数: 1000 cost: 8
 * mappingByApacheBeanUtils 请求次数: 1000 cost: 50
 * mappingByApachePropertyUtils 请求次数: 1000 cost: 9
 * mappingBySpringBeanUtils 请求次数: 10000 cost: 28
 * mappingByCglibBeanCopier 请求次数: 10000 cost: 16
 * mappingByApacheBeanUtils 请求次数: 10000 cost: 47
 * mappingByApachePropertyUtils 请求次数: 10000 cost: 9
 * mappingBySpringBeanUtils 请求次数: 100000 cost: 55
 * mappingByCglibBeanCopier 请求次数: 100000 cost: 31
 * mappingByApacheBeanUtils 请求次数: 100000 cost: 193
 * mappingByApachePropertyUtils 请求次数: 100000 cost: 88
 *
 * copier性能更好 apachebeanutils最差
 * @author zhaozhiwei
 * @date 2021/10/2 下午10:04
 * @version V1.0
 */
public class MapperPropertyDemoTest {

    public static void main(String[] args) {
//        关闭apache的一大堆日志
        final LoggerContext iLoggerFactory = (LoggerContext)LoggerFactory.getILoggerFactory();
        final Logger logger = iLoggerFactory.getLogger("org.apache.commons.beanutils");
        logger.setLevel(Level.toLevel("OFF"));

        final User user = new User();
        user.setId(1);
        user.setName("测试");
        final MapperPropertyDemo proxy = (MapperPropertyDemo)new RequestTimeCostProxy().getInstence(MapperPropertyDemo.class);
        int times = 1000;
//        每次增加十倍请求数量
        for (int i = 0; i < 3; i++) {
            proxy.mappingBySpringBeanUtils(user, times);
            proxy.mappingByCglibBeanCopier(user, times);
            proxy.mappingByApacheBeanUtils(user, times);
            proxy.mappingByApachePropertyUtils(user, times);
            times *= 10;
        }
    }

}
