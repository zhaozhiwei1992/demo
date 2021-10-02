package com.lx.demo.springbootjdbc;

import com.lx.demo.springbootjdbc.domain.User;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;

import java.lang.reflect.InvocationTargetException;

/**
 * @Title: MapperPropertyTestDemo
 * @Package com/lx/demo/springbootjdbc/MapperPropertyTestDemo.java
 * @Description:
 * 测试不同属性复制性能差异
 * mappingBySpringBeanUtils
 * mappingByCglibBeanCopier
 * mappingByApacheBeanUtils
 * mappingByApachePropertyUtils
 * 被代理类MapperPropertyDemo和测试类 MapperPropertyDemoTest不能写到一块，否则无法代理
 * @author zhaozhiwei
 * @date 2021/10/2 下午10:04
 * @version V1.0
 */
public class MapperPropertyDemo {

    public void mappingBySpringBeanUtils(User user, int times) {
        for (int i = 0; i < times; i++) {
            final User userCopy = new User();
            BeanUtils.copyProperties(user, userCopy);
        }
    }

    public void mappingByCglibBeanCopier(User user, int times) {
        for (int i = 0; i < times; i++) {
            final User userCopy = new User();
            final BeanCopier beanCopier = BeanCopier.create(User.class, User.class, false);
            beanCopier.copy(user, userCopy, null);
        }
    }

    public void mappingByApacheBeanUtils(User user, int times) {
        for (int i = 0; i < times; i++) {
            final User userCopy = new User();
            try {
                org.apache.commons.beanutils.BeanUtils.copyProperties(userCopy, user);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public void mappingByApachePropertyUtils(User user, int times) {
        for (int i = 0; i < times; i++) {
            final User userCopy = new User();
            try {
                PropertyUtils.copyProperties(userCopy, user);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }
}
