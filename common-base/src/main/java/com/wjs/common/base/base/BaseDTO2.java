package com.wjs.common.base.base;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
public class BaseDTO2 extends BaseDTO {

    public Integer priority;//优先次序
    public String url;//url地址
    public String method;//方法
    public BigDecimal amount;//金额
    public Long ownerId;//所属外键
    public String describe;//描述
    public String ownerType;//所属实体类型

    //过滤条件
    public Long[] idArr;//查询多个指定ID
    public String[] codeArr;//查询多个指定编码
    public String[] errorCodeArr;//查询多个指定错误码
    public String[] statusArr;//查询多个指定状态
    public Integer[] excludeStatusArr;//排除多个指定状态
    public Date beginCreateTime;//过滤创建时间:开始时间
    public Date endCreateTime;//过滤创建时间:结束时间
    public Date beginUpdateTime;//过滤更新时间:开始时间
    public Date endUpdateTime;////过滤更新时间:结束时间

    //排序属性
    public Boolean orderByIdAsc;//主键升序
    public Boolean orderByIdDesc;//主键降序
    public Boolean orderByAmountAsc;//金额升序
    public Boolean orderByAmountDesc;//金额降序
    public Boolean orderByMemberIdAsc;//拥有人升序
    public Boolean orderByMemberIdDesc;//拥有人降序
    public Boolean orderByNameAsc;//名称升序
    public Boolean orderByNameDesc;//名称降序
    public Boolean orderByMemberNameAsc;//会员名称升序
    public Boolean orderByMemberNameDesc;//会员名称降序
    public Boolean orderByCodeAsc;//编码升序
    public Boolean orderByCodeDesc;//编码降序
    public Boolean orderByErrorCodeAsc;//错误码升序
    public Boolean orderByErrorCodeDesc;//错误码降序
    public Boolean orderByErrorMsgAsc;//错误信息升序
    public Boolean orderByErrorMsgDesc;//错误信息降序
    public Boolean orderByPriorityAsc;//优先级升序
    public Boolean orderByPriorityDesc;//优先级降序
    public Boolean orderByTypeAsc;//类型升序
    public Boolean orderByTypeDesc;//类型降序
    public Boolean orderByStatusAsc;//状态升序
    public Boolean orderByStatusDesc;//状态降序
    public Boolean orderByCreateTimeAsc;//创建时间升序
    public Boolean orderByCreateTimeDesc;//创建时间降序
    public Boolean orderByUpdateTimeAsc;//更新时间升序
    public Boolean orderByUpdateTimeDesc;//更新时间降序

    public BaseDTO2() {
    }

    public BaseDTO2(Throwable throwable) {
        super(throwable);
    }
}
