package com.wjs.common.base.base;

import com.wjs.common.base.annotation.StatusAndClassNum;
import com.wjs.common.base.util.BeanPropertiesUtil;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Date;


/**
 * @ClassName: BaseEntity
 * @Description: 基础实体类
 * @author: 潘清清（pqqkmust@163.com）
 * @date: 2015年08月24日 10时43分00秒
 */
@Setter
@Getter
public class BaseEntity<StatusType> extends BaseObject {

    protected Long id;
    @CreationTimestamp
    protected Date createTime = new Date();
    @UpdateTimestamp
    protected Date updateTime = new Date();
    protected Long version = Long.valueOf(0L);
    protected Boolean logicDelete = Boolean.valueOf(false);
    protected String name;
    protected Integer type = 0;
    protected String memo;
    protected StatusType status;
    protected String statusVal;
    protected String statusDescribe;

    public BaseEntity() {
    }

    public BaseEntity(Long id) {
        this.id = id;
    }

    public String getStatusVal() {
        if (checkStatus()) return null;
        StatusAndClassNum annotation = getStatusAndClassNumAnnotation(status.getClass());
        return annotation == null ? null : annotation.number();
    }

    public String getStatusDescribe() {
        if (checkStatus()) return null;
        StatusAndClassNum annotation = getStatusAndClassNumAnnotation(status.getClass());
        return annotation == null ? null : annotation.describe();
    }

    protected StatusAndClassNum getStatusAndClassNumAnnotation(Class<?> aClass) {
        return AnnotationUtils.findAnnotation(aClass, StatusAndClassNum.class);
    }

    protected boolean checkStatus() {
        return status == null ? true : false;
    }

    public <DTO extends BaseDTO> DTO makeDTO(Class<DTO> dtos) {
        return BeanPropertiesUtil.copyProperties(this, dtos);
    }

    public <DTO extends BaseDTO> void setBaseProperties(DTO dto) {
        this.name = dto.getName();
        this.memo = dto.getMemo();
    }
}
