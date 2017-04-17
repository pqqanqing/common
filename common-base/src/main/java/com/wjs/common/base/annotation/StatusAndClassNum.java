package com.wjs.common.base.annotation;

import java.lang.annotation.*;

/**
 * Created by panqingqing on 16/2/18.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StatusAndClassNum {

    Class<?> superClass();

    String number() default "0";

    String describe() default "描述";

    Class<?> parasitClass();
}
