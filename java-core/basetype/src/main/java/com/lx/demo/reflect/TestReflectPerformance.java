package com.lx.demo.reflect;

import com.esotericsoftware.reflectasm.MethodAccess;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.time.StopWatch;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 */
public class TestReflectPerformance {

    private long times = 100_000_000L;
    private SimpleBean bean;
    private String formatter = "%s %d times using %d ms";

    public TestReflectPerformance() {
        bean = new SimpleBean();
        bean.setName("反射性能测试");
    }

    /**
     * @data: 2021/5/8-下午2:31
     * @User: zhaozhiwei
     * @method: directGet

     * @return: void
     * @Description:     //直接通过Java的get方法
     */
    public void directGet() {
        StopWatch watch = StopWatch.createStarted();
        for (long i = 0; i < times; i++) {
            bean.getName();
        }
        watch.stop();
        String result = String.format(formatter, "directGet", times, watch.getTime());
        System.out.println(result);
    }
    /**
     * @data: 2021/5/8-下午2:32
     * @User: zhaozhiwei
     * @method: reflectAsmGet

     * @return: void
     * @Description: 通过高性能的ReflectAsm库进行测试，仅进行一次methodAccess获取
     */
    public void reflectAsmGet() {
        MethodAccess methodAccess = MethodAccess.get(SimpleBean.class);
        StopWatch watch = StopWatch.createStarted();
        for (long i = 0; i < times; i++) {
            methodAccess.invoke(bean, "getName");
        }
        watch.stop();
        String result = String.format(formatter, "reflectAsmGet", times, watch.getTime());
        System.out.println(result);
    }
    /**
     * @data: 2021/5/8-下午2:32
     * @User: zhaozhiwei
     * @method: javaReflectGet

     * @return: void
     * @Description: 通过Java Class类自带的反射获得Method测试，仅进行一次method获取
     */
    public void javaReflectGet() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Method getName = SimpleBean.class.getMethod("getName");
        StopWatch watch = StopWatch.createStarted();
        for (long i = 0; i < times; i++) {
            getName.invoke(bean);
        }
        watch.stop();
        String result = String.format(formatter, "javaReflectGet", times, watch.getTime());
        System.out.println(result);
    }
    /**
     * @data: 2021/5/8-下午2:32
     * @User: zhaozhiwei
     * @method: propertyGet

     * @return: void
     * @Description: 使用Java自带的Property属性获取Method测试，仅进行一次method获取
     */
    public void propertyGet() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, IntrospectionException, IntrospectionException {
        Method method = null;
        BeanInfo beanInfo = Introspector.getBeanInfo(SimpleBean.class);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if (propertyDescriptor.getName().equals("name")) {
                method = propertyDescriptor.getReadMethod();
                break;
            }
        }
        StopWatch watch = StopWatch.createStarted();
        for (long i = 0; i < times; i++) {
           
            method.invoke(bean);
        }
        watch.stop();
        String result = String.format(formatter, "propertyGet", times, watch.getTime());
        System.out.println(result);
    }
    /**
     * @data: 2021/5/8-下午2:32
     * @User: zhaozhiwei
     * @method: beanUtilsGet

     * @return: void
     * @Description: BeanUtils的getProperty测试
     */
    public void beanUtilsGet() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        StopWatch watch = StopWatch.createStarted();
        for (long i = 0; i < times; i++) {
            BeanUtils.getProperty(bean, "name");
        }
        watch.stop();
        String result = String.format(formatter, "beanUtilsGet", times, watch.getTime());
        System.out.println(result);
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException,
            NoSuchMethodException, IntrospectionException {
        final TestReflectPerformance testIterator = new TestReflectPerformance();
        testIterator.directGet();
        testIterator.beanUtilsGet();
        testIterator.javaReflectGet();
        testIterator.propertyGet();
        testIterator.reflectAsmGet();
    }
}