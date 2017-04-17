package com.wjs.common.base.util;


import com.wjs.common.base.annotation.Dto2Entity;
import com.wjs.common.base.annotation.StatusAndClassNum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.*;

import static com.wjs.common.base.constant.Constant.POINT;
import static com.wjs.common.base.constant.Constant.SLASH;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by panqingqing on 16/6/2.
 */
public class ScanClassUtil {

    public static final String CLASS = ".class";
    private static String[] workspaces = {"com.grace", "cn.com.common", "com.wjs"};
    private static List<Class<?>> classes = new ArrayList<Class<?>>();
    private static Map<String, Object> classMap = new HashMap<String, Object>();
    //statusMap 代表跟StatusAndClassNum这个注解相关的所有map
    private static Map<String, Object> statusMap = new HashMap<String, Object>();

    static {
        for (String workspace : workspaces) {
            Set<String> classPathNameByPackage = ClassUtil.scanClassPathNameByPackage(workspace, CLASS, true);
            for (String name : classPathNameByPackage) {
                name = name.replace(SLASH, ".");
                name = name.replace("\\", ".");
                if (!name.endsWith(".class")) continue;
                String className = StringUtils.substringBefore(name.replace(SLASH, POINT), CLASS);
                try {
                    classes.add(Class.forName(className));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
        //组装各个注解扫描(每个注解都有各自的特点,所以分开组装)
        assembleStatusAndClassNumClassMap();
        assembleDto2EntityClassMap();
    }

    private static void assembleStatusAndClassNumClassMap() {
        String classValue = null;
        Map<String, Class> map = new HashMap<String, Class>();
        for (Class c : classes) {
            StatusAndClassNum annotation = AnnotationUtils.findAnnotation(c, StatusAndClassNum.class);
            if (isNull(annotation)) continue;
            if (nonNull(classMap.get(annotation.superClass() + annotation.number())))
                throw new RuntimeException(annotation.superClass() + annotation.number() + "对应的value值已经存在,不能重复添加" + StatusAndClassNum.class + "对应的注解!");
            Object object = ClassUtil.newInstance(c);
            classMap.put(annotation.superClass() + annotation.number(), object);
            statusMap.put(annotation.parasitClass() + annotation.number(), object);
        }
    }

    private static void assembleDto2EntityClassMap() {
        String classValue = null;
        Map<String, Class> map = new HashMap<String, Class>();
        for (Class c : classes) {
            Dto2Entity annotation = AnnotationUtils.findAnnotation(c, Dto2Entity.class);
            if (isNull(annotation)) continue;
            if (nonNull(classMap.get(annotation.dto().getName())))
                throw new RuntimeException(annotation.dto().getName() + "对应的value值已经存在,不能重复添加" + Dto2Entity.class + "对应的注解!");
            //Object object = ClassUtil.newInstance(c);//找到对应的class就好,没有必要实例化对象
            classMap.put(annotation.dto().getName(), c);
        }
    }

    public static Map<String, Object> getClassMap() {
        return classMap;
    }

    public static Map<String, Object> getStatusMap() {
        return statusMap;
    }

    public static Object getObject(String key) {
        return isNull(key) ? null : classMap.get(key);
    }

}
