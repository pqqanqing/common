package com.wjs.common.base.util;

import org.dozer.DozerBeanMapper;

/**
 * Created by panqingqing on 16/2/15.
 */
public class BeanPropertiesUtil {

    private static DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();

    public static <T> T copyProperties(Object source, Class<T> destination) {
        return dozerBeanMapper.map(source, destination);
    }

    public static void copyProperties(Object source, Object destination) {
        dozerBeanMapper.map(source, destination);
    }

}
