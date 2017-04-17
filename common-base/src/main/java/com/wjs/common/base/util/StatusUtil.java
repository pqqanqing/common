package com.wjs.common.base.util;

import com.wjs.common.base.annotation.StatusAndClassNum;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * Created by panqingqing on 16/2/19.
 */
public class StatusUtil {

    public static Integer getStatusVal(Object object, String flag) {
        return (Integer) getStatusValue(object, flag);
    }

    public static String getStatusDescribe(Object object, String flag) {
        return (String) getStatusValue(object, flag);
    }

    public static <T> T getStatusValue(T t, String flag) {
        if (t == null) return null;
        StatusAndClassNum statusAnnotation = t.getClass().getDeclaredAnnotation(StatusAndClassNum.class);
        Object value = AnnotationUtils.getValue(statusAnnotation, flag);
        return (T) value;
    }
}
