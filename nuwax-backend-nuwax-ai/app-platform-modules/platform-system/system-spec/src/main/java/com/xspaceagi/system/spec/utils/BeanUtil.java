package com.xspaceagi.system.spec.utils;

import lombok.extern.slf4j.Slf4j;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自省工具
 */
@Slf4j
public class BeanUtil {


    static Map<String, Map<String, Method>> readBeanMap = new ConcurrentHashMap<>();//读取方法
    static Map<String, Map<String, Method>> writeBeanMap = new ConcurrentHashMap<>();//写方法

    /**
     * 从缓存获取字段信息
     *
     * @param tBean
     * @param <T>
     * @return
     * @throws IntrospectionException
     */
    public static <T> Map<String, Method> getReadBeanMap(T tBean) throws IntrospectionException {
        final String beanName = tBean.getClass().getName();//bean的名称
        final Map<String, Method> result = readBeanMap.get(beanName);
        if (result == null) {
            BeanInfo beanInfo = Introspector.getBeanInfo(tBean.getClass());
            Map<String, Method> readMap = new HashMap<String, Method>();//读取方法
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String name = property.getName();
                Method getMethod = property.getReadMethod();
                if (getMethod != null) {
                    readMap.put(name, getMethod);
                }
            }
            readBeanMap.put(beanName, readMap);
            return readMap;
        } else {
            return result;
        }
    }

    /**
     * 检查属性是否存在
     *
     * @param beanInfo bean信息
     * @param keyName  属性名称
     * @return
     */
    private static boolean checkPropertyExist(BeanInfo beanInfo, String keyName) {
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            final String propertyName = propertyDescriptor.getName();
            if (keyName.equals(propertyName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取写属性
     *
     * @param tBean 对象
     * @param <T>   任意对象
     * @return
     * @throws IntrospectionException
     */
    private static <T> Map<String, Method> getWriteBeanMap(T tBean) throws IntrospectionException {
        final String beanName = tBean.getClass().getName();//bean的名称
        final Map<String, Method> result = writeBeanMap.get(beanName);
        if (result == null) {
            BeanInfo beanInfo = Introspector.getBeanInfo(tBean.getClass());
            Map<String, Method> writeMap = new HashMap<String, Method>();//写方法
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String name = property.getName();
                Method setmethod = property.getWriteMethod();
                if (setmethod != null) {
                    writeMap.put(name, setmethod);
                }
            }
            writeBeanMap.put(beanName, writeMap);
            return writeMap;
        } else {
            return result;
        }
    }


    /**
     * 获取读属性值
     *
     * @param tBean   对象
     * @param keyName 属性名
     * @param <T>     任意对象
     * @return
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static <T> Object readBeanPropertyValue(T tBean, String keyName) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        BeanInfo beanInfo = Introspector.getBeanInfo(tBean.getClass());
        boolean flag = checkPropertyExist(beanInfo, keyName);
        if (!flag) return null;
        Map<String, Method> readMap = BeanUtil.getReadBeanMap(tBean);//读取方法
        Method getMethod = readMap.get(keyName);
        if (Objects.isNull(getMethod)) {
            return null;
        }
        Object val = getMethod.invoke(tBean);
        return val;
    }

    /**
     * 获取读属性值(字符串)
     *
     * @param tBean   对象
     * @param keyName 属性名
     * @param <T>     任意对象
     * @return
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static <T> String readStringBeanPropertyValue(T tBean, String keyName) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        BeanInfo beanInfo = Introspector.getBeanInfo(tBean.getClass());
        boolean flag = checkPropertyExist(beanInfo, keyName);
        if (!flag) return null;
        Map<String, Method> readMap = BeanUtil.getReadBeanMap(tBean);//读取方法
        Method getMethod = readMap.get(keyName);
        Object val = getMethod.invoke(tBean);
        if (val != null) {
            return val.toString();
        }
        return null;
    }

    /**
     * 获取读属性值(字符串)
     *
     * @param tBean   对象
     * @param keyName 属性名
     * @param <T>     任意对象
     * @return
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static <T> Integer readIntegerBeanPropertyValue(T tBean, String keyName) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        BeanInfo beanInfo = Introspector.getBeanInfo(tBean.getClass());
        boolean flag = checkPropertyExist(beanInfo, keyName);
        if (!flag) return null;
        Map<String, Method> readMap = BeanUtil.getReadBeanMap(tBean);//读取方法
        Method getMethod = readMap.get(keyName);
        Object val = getMethod.invoke(tBean);
        if (val != null) {
            return Integer.valueOf(val.toString());
        }
        return 0;
    }

    /**
     * 检查属性是否存在
     *
     * @param tClass  对象类
     * @param keyName 属性名
     * @param <T>     任意对象
     * @return true表示存在
     * @throws IntrospectionException
     */
    public static <T> boolean checkPropertyExist(Class<T> tClass, String keyName) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(tClass);
        return checkPropertyExist(beanInfo, keyName);
    }


    /**
     * 写入属性值
     *
     * @param tBean
     * @param keyName
     * @param value
     * @param <T>
     * @return
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static <T> boolean writeBeanPropertyValue(T tBean, String keyName, Object value) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        BeanInfo beanInfo = Introspector.getBeanInfo(tBean.getClass());
        boolean flag = checkPropertyExist(beanInfo, keyName);
        if (!flag) return false;
        Map<String, Method> writeMap = BeanUtil.getWriteBeanMap(tBean);//读取方法
        Method setMethod = writeMap.get(keyName);
        setMethod.invoke(tBean, value);
        return true;
    }


    public static boolean isPrimitive(Field field) {
        if (Objects.isNull(field)) {
            return false;
        }
        return field.getType().isPrimitive();
    }
}
