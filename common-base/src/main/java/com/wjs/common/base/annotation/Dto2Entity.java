package com.wjs.common.base.annotation;


import com.wjs.common.base.base.BaseDTO;

import java.lang.annotation.*;

/**
 * 根据dto的class找到对应的实体class
 * Created by panqingqing on 16/8/16.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Dto2Entity {

    Class<? extends BaseDTO> dto();

}
