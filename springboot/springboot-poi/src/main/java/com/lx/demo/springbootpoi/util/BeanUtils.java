package com.lx.demo.springbootpoi.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.lx.demo.springbootpoi.annotation.PropertyName;

/**
 * Bean操作工具类
 * 
 */
public class BeanUtils {

	public static Field getField(Object obj, String fieldName) {
		Class<?> clazz = obj.getClass();
		try {
			Method method = clazz.getMethod("isProxy", new Class[0]);
			Boolean result = (Boolean) method.invoke(obj, new Object[0]);
			if (result.booleanValue()) {
				clazz = clazz.getSuperclass();
			}
		} catch (Exception localException) {
		}
		Field field = null;
		for (Class<?> c = clazz; c != Object.class; c = c.getSuperclass()) {
			try {
				field = c.getDeclaredField(fieldName);
				break;
			} catch (Exception e) {
			}
		}
		return field;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<String> getFields(Object obj) {
		Class clazz = obj.getClass();
		try {
			Method method = clazz.getMethod("isProxy", new Class[0]);
			Boolean result = (Boolean) method.invoke(obj, new Object[0]);
			if (result.booleanValue()) {
				clazz = clazz.getSuperclass();
			}
		} catch (Exception localException) {
		}

		List<String> list = new ArrayList<String>();
		for (Class<?> c = clazz; c != Object.class; c = c.getSuperclass()) {
			for (Field f : c.getFields()) {
				list.add(f.getName());
			}
			for (Method m : c.getDeclaredMethods()) {
				String mname = m.getName();
				if ("getClass".equals(mname)) {
					continue;
				}
				if (mname.startsWith("get")) {
					String pname = mname.substring(3);
					if (pname.length() == 1) {
						pname = pname.toLowerCase();
					} else {
						pname = pname.substring(0, 1).toLowerCase() + pname.substring(1);
					}
					list.add(pname);
				} else if (mname.startsWith("is")) {
					String pname = mname.substring(2);
					if (pname.length() == 1) {
						pname = pname.toLowerCase();
					} else {
						pname = pname.substring(0, 1).toLowerCase() + pname.substring(1);
					}
					list.add(pname);
				}
			}
		}
		return list;
	}

	/**
	 * 获取对象的属性
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> getAttributes(Object obj) {
		Class clazz = obj.getClass();
		try {
			Method method = clazz.getMethod("isProxy", new Class[0]);
			Boolean result = (Boolean) method.invoke(obj, new Object[0]);
			if (result.booleanValue()) {
				clazz = clazz.getSuperclass();
			}
		} catch (Exception localException) {
		}

		Map attr = new HashMap();
		for (Class<?> c = clazz; c != Object.class; c = c.getSuperclass()) {
			for (Field f : c.getFields()) {
				try {
					Object value = f.get(obj);
					attr.put(f.getName(), value);
				} catch (Exception localException1) {
				}
			}
			for (Method m : c.getDeclaredMethods()) {
				String mname = m.getName();
				if ("getClass".equals(mname)) {
					continue;
				}
				if (mname.startsWith("get")) {
					String pname = mname.substring(3);
					if (pname.length() == 1) {
						pname = pname.toLowerCase();
					} else {
						pname = pname.substring(0, 1).toLowerCase() + pname.substring(1);
					}
					try {
						Object value = m.invoke(obj, new Object[0]);
						attr.put(pname, value);
					} catch (Exception localException2) {
					}
				} else if (mname.startsWith("is")) {
					String pname = mname.substring(2);
					if (pname.length() == 1) {
						pname = pname.toLowerCase();
					} else {
						pname = pname.substring(0, 1).toLowerCase() + pname.substring(1);
					}
					try {
						Object value = m.invoke(obj, new Object[0]);
						attr.put(pname, value);
					} catch (Exception localException3) {
					}
				}
			}
		}
		return (Map<String, Object>) attr;
	}

	/**
	 * 实体类的每个字段转为map
	 * @param obj
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> getAttributes(Object obj, Map<String, Object> map) {
		Class clazz = obj.getClass();
		try {
			Method method = clazz.getMethod("isProxy", new Class[0]);
			Boolean result = (Boolean) method.invoke(obj, new Object[0]);
			if (result.booleanValue()) {
				clazz = clazz.getSuperclass();
			}
		} catch (Exception localException) {
		}

		for (Class<?> c = clazz; c != Object.class; c = c.getSuperclass()) {
			for (Field f : c.getFields()) {
				try {
					Object value = f.get(obj);
					map.put(f.getName(), value);
				} catch (Exception localException1) {
				}
			}
			for (Method m : c.getDeclaredMethods()) {
				String mname = m.getName();
				if ("getClass".equals(mname)) {
					continue;
				}
				if (mname.startsWith("get")) {
					String pname = mname.substring(3);
					if (pname.length() == 1) {
						pname = pname.toLowerCase();
					} else {
						pname = pname.substring(0, 1).toLowerCase() + pname.substring(1);
					}
					try {
						Object value = m.invoke(obj, new Object[0]);
						map.put(pname, value);
					} catch (Exception localException2) {
					}
				} else if (mname.startsWith("is")) {
					String pname = mname.substring(2);
					if (pname.length() == 1) {
						pname = pname.toLowerCase();
					} else {
						pname = pname.substring(0, 1).toLowerCase() + pname.substring(1);
					}
					try {
						Object value = m.invoke(obj, new Object[0]);
						map.put(pname, value);
					} catch (Exception localException3) {
					}
				}
			}
		}
		return map;
	}

	/**
	 * 实体类每个字段的name和注释转为map
	 * @param obj
	 * @param map
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static Map<String, Object> getFieldAndProperty(Object obj, Map<String, Object> map) throws Exception {
		Class<?> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			PropertyName p = field.getAnnotation(PropertyName.class);
			if (p != null && !"id".equals(field.getName())) {
				map.put(field.getName(), p.value());
			}
		}
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, String> getFieldNameAndType(Object obj) {
		Class clazz = obj.getClass();
		try {
			Method method = clazz.getMethod("isProxy", new Class[0]);
			Boolean result = (Boolean) method.invoke(obj, new Object[0]);
			if (result.booleanValue()) {
				clazz = clazz.getSuperclass();
			}
		} catch (Exception localException) {
		}

		//List<String> list = new ArrayList<String>();
		Map<String, String> map = new HashMap<String, String>();
		for (Class<?> c = clazz; c != Object.class; c = c.getSuperclass()) {
			for (Field f : c.getFields()) {
				map.put(f.getName(), f.getType().toString());
			}
			for (Method m : c.getDeclaredMethods()) {
				String mname = m.getName();
				if (mname.equals("getClass")) {
					continue;
				}
				if (mname.startsWith("get")) {
					String pname = mname.substring(3);
					if (pname.length() == 1) {
						pname = pname.toLowerCase();
					} else {
						pname = pname.substring(0, 1).toLowerCase() + pname.substring(1);
					}
					map.put(pname, m.getReturnType().getName());

				} else if (mname.startsWith("is")) {
					String pname = mname.substring(2);
					if (pname.length() == 1) {
						pname = pname.toLowerCase();
					} else {
						pname = pname.substring(0, 1).toLowerCase() + pname.substring(1);
					}
					map.put(pname, m.getReturnType().getName());
				}
			}
		}
		return map;
	}

	/**
	 * 功能描述：获得指定接口的所有子类
	 * <p>
	 * @author 李斌
	 * <p>
	 * @date 2017年10月31日 下午3:21:42
	 * 
	 * @param clazz 接口类
	 * @return
	 */
	public static List<Class<?>> getAllSubclassOfInterface(Class<?> clazz) {
		Field field = null;
		Vector<?> v = null;
		List<Class<?>> allSubclass = new ArrayList<Class<?>>();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Class<?> classOfClassLoader = loader.getClass();
		Class<?> interfaceClass = null;
		try {
			interfaceClass = (Class<?>) Class.forName(clazz.getName());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("无法获取到" + clazz.getName() + "的Class对象，请查看包名或路径是否正确");
		}
		while (classOfClassLoader != ClassLoader.class) {
			classOfClassLoader = classOfClassLoader.getSuperclass();
		}
		try {
			field = classOfClassLoader.getDeclaredField("classes");
		} catch (NoSuchFieldException e) {
			throw new RuntimeException("无法获取到当前线程的类加载器的classes域");
		}
		field.setAccessible(true);
		try {
			v = (Vector<?>) field.get(loader);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("无法从类加载器中获取到类属性");
		}
		for (int i = 0; i < v.size(); ++i) {
			Class<?> c = (Class<?>) v.get(i);
			if (interfaceClass.isAssignableFrom(c) && !interfaceClass.equals(c) && !clazz.equals(c)) {
				allSubclass.add(c);
			}
		}
		return allSubclass;
	}

}
