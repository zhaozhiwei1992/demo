package com.lx.demo.javabeans;

import java.beans.*;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

public class JavaBeanDemo {
    public static void main(String[] args) {
        try {
            Class<?> forName = Class.forName("com.lx.demo.javabeans.User");
            BeanInfo beanInfo = Introspector.getBeanInfo(forName, Object.class);
            System.out.println(beanInfo);
            //方法描述
//            MethodDescriptor[] descriptors = beanInfo.getMethodDescriptors();
//            Stream.of(descriptors).forEach(System.out::println);

            User user = new User();
            //字段描述符
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
//            Stream.of(propertyDescriptors).forEach(System.out::println);
            Stream.of(propertyDescriptors).forEach(propertyDescriptor -> {
//                propertyDescriptor.getValue("id");
//                System.out.println(propertyDescriptor.getName());
                if("id".equals(propertyDescriptor.getName())){
                    propertyDescriptor.setPropertyEditorClass(IDPropertyEditor.class);
                    PropertyEditor propertyEditor = propertyDescriptor.createPropertyEditor(user);
                    //在這裡倆種方式設置值都是假的，需要通過事件監聽來設置
                    propertyEditor.addPropertyChangeListener(evt -> {
//                        Object newValue = evt.getNewValue();
                        PropertyEditor source = (PropertyEditor) evt.getSource();
                        try {
                            //底層處理沒有給這邊傳數據...        PropertyChangeEvent evt = new PropertyChangeEvent(source, null, null, null);.
//                            propertyDescriptor.getWriteMethod().invoke(user, newValue);
                            propertyDescriptor.getWriteMethod().invoke(user, source.getValue());
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    });
//                    propertyEditor.setValue(1);
                    propertyEditor.setAsText("1");
                    //在这里需要有propertiesedit
                    /*
                    * final Class<?> cls = getPropertyEditorClass();
        if (cls != null && PropertyEditor.class.isAssignableFrom(cls)
                && ReflectUtil.isPackageAccessible(cls)) { */
                }else if("date".equals(propertyDescriptor.getName())){
                    propertyDescriptor.setPropertyEditorClass(DatePropertyEditor.class);
                    PropertyEditor propertyEditor = propertyDescriptor.createPropertyEditor(user);
//                    propertyEditor.addPropertyChangeListener(evt -> {
//                        PropertyEditor source = (PropertyEditor) evt.getSource();
//                        try {
//                            //底層處理沒有給這邊傳數據...        PropertyChangeEvent evt = new PropertyChangeEvent(source, null, null, null);.
////                            propertyDescriptor.getWriteMethod().invoke(user, newValue);
//                            propertyDescriptor.getWriteMethod().invoke(user, source.getValue());
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        } catch (InvocationTargetException e) {
//                            e.printStackTrace();
//                        }
//                    });
                    propertyEditor.addPropertyChangeListener(new SetPropertyListener(propertyDescriptor, user));

                    propertyEditor.setAsText("2018-08-03");
                }
            });
            System.out.println(user);
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 可重用屬性設置監聽實現
     */
    public static class SetPropertyListener implements PropertyChangeListener {

        private PropertyDescriptor propertyDescriptor;
        private Object bean;

        public SetPropertyListener(PropertyDescriptor propertyDescriptor, Object bean) {
            this.propertyDescriptor = propertyDescriptor;
            this.bean = bean;
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            PropertyEditor source = (PropertyEditor) evt.getSource();
            try {
                //底層處理沒有給這邊傳數據...        PropertyChangeEvent evt = new PropertyChangeEvent(source, null, null, null);.
//                            propertyDescriptor.getWriteMethod().invoke(user, newValue);
                propertyDescriptor.getWriteMethod().invoke(bean, source.getValue());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
