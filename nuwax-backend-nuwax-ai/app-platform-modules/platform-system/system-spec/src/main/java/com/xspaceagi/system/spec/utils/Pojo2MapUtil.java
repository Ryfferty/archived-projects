package com.xspaceagi.system.spec.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson2.JSON;
import com.xspaceagi.system.spec.annotation.NoParseRequestParam;

import lombok.extern.slf4j.Slf4j;

/**
 * bean 转map 工具类
 *
 * @author soddy
 */
@Slf4j
public class Pojo2MapUtil {

    private static final ConcurrentHashMap<String, List<Field>> zClassFieldMap = new ConcurrentHashMap<>();

    /**
     * 解析缓存 对象字段
     *
     * @param clazz 对象
     * @return 字段
     */
    private static List<Field> getClassFieldList(Class clazz) {
        String clazzName = clazz.getName();
        List<Field> fields = zClassFieldMap.get(clazzName);
        if (Objects.isNull(fields)) {

            List<Field> fieldList = new ArrayList<>();
            Class tempClass = clazz;
            while (tempClass != null) {
                // 当父类为null的时候说明到达了最上层的父类(Object类).
                fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
                // 得到父类,然后赋给自己
                tempClass = tempClass.getSuperclass();
            }
            zClassFieldMap.put(clazzName, fieldList);
            return fieldList;
        } else {
            return fields;
        }

    }

    /**
     * 实体对象转成Map
     *
     * @param obj 实体对象
     * @return map
     */
    public static Map<String, Object> object2Map(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        List<Field> fieldList = getClassFieldList(clazz);

        try {
            for (Field field : fieldList) {
                if (field.getName().equalsIgnoreCase("log")) {
                    // log 字段属性主动忽略, 防止JSONUtil 打印map报错
                    continue;
                }
                NoParseRequestParam noParseRequestParam = field.getAnnotation(NoParseRequestParam.class);
                if (noParseRequestParam != null) {
                    // 有此注解字段,不解析到queryMap里
                    if (log.isDebugEnabled()) {
                        log.debug("[@NoParseRequestParam]使用了此注解,不解析到queryMap筛选条件里");
                    }
                } else {
                    Object value = BeanUtil.readBeanPropertyValue(obj, field.getName());
                    // 过滤value 值为空的
                    if (null != value) {
                        map.put(field.getName(), value);
                    }
                }

            }
        } catch (Exception e) {
            log.error("bean转map失败,入参={}", JSON.toJSONString(obj), e);
        }
        return map;
    }

}
