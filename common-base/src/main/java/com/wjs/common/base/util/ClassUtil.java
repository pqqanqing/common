package com.wjs.common.base.util;

import com.wjs.common.base.base.BaseDTO;
import com.wjs.common.base.base.BaseQueryBuilder;
import com.wjs.common.base.constant.Constant;
import com.wjs.common.base.filter.ClassPathNameFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static com.wjs.common.base.constant.Constant.*;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Created by panqingqing on 16/5/4.
 */
public class ClassUtil {

    /**
     * 系统日志输出句柄
     */
    private static Logger logger = LoggerFactory.getLogger(ClassUtil.class);

    public static Set<String> scanClassPathNameByPackage(String packageName, String matchParam, boolean recursive) {
        Set<String> classPathName = new LinkedHashSet<String>();
        String packageDirName = packageName.replace(Constant.POINT, SLASH);
        Enumeration<URL> dirs = null;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile(), UTF8);
                    findAndAddClassPathNameInPackageByFile(packageDirName, filePath, matchParam, recursive, classPathName);
                } else if ("jar".equals(protocol)) {
                    scanClassPathNameByJar(packageDirName, url, matchParam, recursive, classPathName);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
        return classPathName;
    }

    public static void findAndAddClassPathNameInPackageByFile(String packageDirName, String packagePath, String matchParam, boolean recursive, Set<String> classPathName) {
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            logger.error("用户定义包名 " + packageDirName + " 下没有任何文件");
            return;
        }
        File[] dirfiles = dir.listFiles(new ClassPathNameFileFilter(matchParam, recursive));
        for (File file : dirfiles) {
            if (file.isDirectory()) {
                findAndAddClassPathNameInPackageByFile(packageDirName, file.getAbsolutePath(), matchParam, recursive, classPathName);
            } else {
                String temp = StringUtils.substringAfter(file.getPath(), packageDirName);
                if (isBlank(temp)) {
                    packageDirName = packageDirName.replace("/", "\\");
                    temp = StringUtils.substringAfter(file.getPath(), packageDirName);
                }
                classPathName.add(packageDirName + temp);
            }
        }
    }

    public static void scanClassPathNameByJar(String packageDirNmae, URL url, String matchParam, boolean recursive, Set<String> classPathName) {
        //扫描jar文件
        JarFile jarFile = null;
        try {
            jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            String jarEntryName = jarEntry.getName();
            //是否是目录,以及是否递归调用
            if (jarEntry.isDirectory() && recursive) {
                continue;
            }
            //是否匹配包前缀
            if (!jarEntryName.startsWith(packageDirNmae)) {
                continue;
            }
            //是否匹配类型后缀
            if (!jarEntryName.endsWith(matchParam)) {
                continue;
            }
            //添加
            classPathName.add(jarEntryName.replace(SLASH, POINT));
        }
    }

    public static <T> T newInstance(Class<T> c) {
        // 默认public构造函数不可用的情况下,寻找单例方法创建实例
        try {
            Method[] declaredMethods = c.getDeclaredMethods();
            for (Method method : declaredMethods) {
                if ("getInstance".equals(method.getName()) && Modifier.isStatic(method.getModifiers()))
                    return (T) method.invoke(c);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        // 有默认public构造函数的情况下，优先使用默认构造函数创建实例
        /*try {
            return c.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }*/
        try {
            Constructor<T> defaultConstructor = c.getConstructor();
            if (Modifier.isPublic(defaultConstructor.getModifiers())) return defaultConstructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        throw new RuntimeException("产生" + c.getName() + "的实例对象失败,请检查该类是否有public构造方法或者单例方法名是否为" + "getInstance");
    }

    public static boolean equals(Object object1, Object object2) {
        if (object1 == null || object2 == null) return false;
        if (object1.getClass() != object2.getClass()) return false;
        if (object1 == object2) return true;
        return false;
    }

    public static Class<Object> getSuperClassGenricType(final Class clazz, final int index) {
        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        //返回表示此类型实际类型参数的 Type 对象的数组。
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }

    public static <DTO extends BaseDTO> void useGetCallSet(BaseQueryBuilder baseQueryBuilder, DTO dto) {
        copyObject2Object(dto, baseQueryBuilder, true);
    }

    //因为DTO调用set方法,这样DTO的get方法就会有值,判断get方法值,如果不为空,则肯定set了,这样就可以通过get方法的方法名来调用BaseQueryBuilder里面的set方法,来动态构造离线查询
    /*public static <DTO extends BaseDTO> void useGetCallSet(BaseQueryBuilder baseQueryBuilder, DTO dto) {
        Method[] dtoMethods = dto.getClass().getMethods();
        for (Method method : dtoMethods) {
            if (isValidMethod(method) && isMatchGet(method)) {
                try {
                    Object invoke = method.invoke(dto);
                    if (invoke != null) callBuilderSet(baseQueryBuilder, method, invoke);
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
    }*/

    public static void copyObject2Object(Object src, Object dest, Boolean flag) {
        Method[] methods = null;
        //是否深调用,就是是否调父类方法
        if (flag) {
            methods = src.getClass().getMethods();
        } else {
            methods = src.getClass().getDeclaredMethods();
        }
        for (Method method : methods) {
            if (isValidMethod(method) && isMatchGet(method)) {
                try {
                    Object invoke = method.invoke(src);
                    if (invoke != null) callDestSet(dest, method, invoke);
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
    }

    public static void callDestSet(Object dest, Method method, Object invoke) {
        String name = generateMethod(method, invoke);
        /*try {
            Method builderMethod = dest.getClass().getMethod(name, method.getReturnType());
            builderMethod.invoke(dest, invoke);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }*/
        //下面这种方法会防止异常信息,但是需要循环
        Method[] methods = dest.getClass().getMethods();
        for (Method builderMethod : methods) {
            if (name.equals(builderMethod.getName())) {
                try {
                    builderMethod.invoke(dest, invoke);
                    break;
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
    }

    public static String generateMethod(Method method, Object invoke) {
        String name = method.getName().substring(3);
        if (invoke.equals(boolean.class)) return "is" + name;
        return "set" + name;
    }

    public static boolean isMatchGet(Method method) {
        return method.getName().startsWith("get");
    }

    public static boolean isValidMethod(Method method) {
        int modifiers = method.getModifiers();
        return Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers) && !Modifier.isFinal(modifiers);
    }

    public static Class<?> getClass(String path) {
        try {
            Class<?> aClass = Class.forName(path);
            return aClass;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}