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
    /**
     * 所属应用编号
     */
    private String appId;
    /**
     * 请求编号
     */
    private String requestId;
    /**
     * 请求时间
     */
    private Date requestTime;
    /**
     * 使用的交互协议版本号
     */
    private String version;
    /**
     * 认证方式
     */
    private String authType;
    /**
     * 认证信息
     */
    private String authInfo;

    public RpcRequest() {
    }
}
