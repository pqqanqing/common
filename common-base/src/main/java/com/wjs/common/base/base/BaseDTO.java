package com.wjs.common.base.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@JsonIgnoreProperties(value = {"basePageResults", "updateTime", "version", "logicDelete"}, allowSetters = true)
public class BaseDTO extends BaseObject {
    public Long id;//主键
    public Date createTime;//创建时间
    public Date updateTime;//更新时间
    public Long version;//版本号
    public Boolean logicDelete;//逻辑删除

    public Integer type;//类型
    public String name;//名称
    public String memo;//备注
    public String statusVal;//状态值
    public String statusDescribe;//状态描述

    //分页信息
    public BasePageResults basePageResults;

    public BaseDTO() {
    }

    public BaseDTO(Throwable throwable) {
        super(throwable);
    }

}
