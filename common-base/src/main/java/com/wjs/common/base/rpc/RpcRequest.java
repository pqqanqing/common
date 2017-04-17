package com.wjs.common.base.rpc;

import com.wjs.common.base.base.BaseObject;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author panqingqing
 * @title 请求的头信息
 * Created by panqingqing on 16/1/30.
 */
@Setter
@Getter
public class RpcRequest extends BaseObject {
    private String appId;//所属应用编号
    private String requestId;//请求编号
    private Date requestTime;//请求时间
    private String version;//使用的交互协议版本号
    private String authType;//认证方式
    private String authInfo;//认证信息

    public RpcRequest() {
    }
}
