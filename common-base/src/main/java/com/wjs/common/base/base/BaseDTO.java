package com.wjs.common.base.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@JsonIgnoreProperties(value = {"basePageResults", "updateTime", "version", "logicDelete"}, allowSetters = true)
public class BaseDTO extends BaseObject {
    /**
     * 主键
     */
    public Long id;
    /**
     * 创建时间
     */
    public Date createTime;
    /**
     * 更新时间
     */
    public Date updateTime;
    /**
     * 版本号
     */
    public Long version;
    /**
     * 逻辑删除
     */
    public Boolean logicDelete;

    /**
     * 类型
     */
    public Integer type;
    /**
     * 名称
     */
    public String name;
    /**
     * 备注
     */
    public String memo;
    /**
     * 状态值
     */
    public String statusVal;
    /**
     * 状态描述
     */
    public String statusDescribe;

    /**
     * 分页信息
     */
    public BasePageResults basePageResults;

    public BaseDTO() {
    }

    public BaseDTO(Throwable throwable) {
        super(throwable);
    }

}
