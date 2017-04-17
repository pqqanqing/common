package com.wjs.common.base.rpc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wjs.common.base.base.BaseObject;
import com.wjs.common.base.base.ResponseResult;
import lombok.Getter;
import lombok.Setter;

import static com.wjs.common.base.base.ResponseResult.CODE_SUCC;
import static com.wjs.common.base.base.ResponseResult.SUCC;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author panqingqing
 * @title 远程应答信息基础类
 * Created by panqingqing on 16/6/20.
 */
@Setter
@Getter
public class RpcResponse<DTO> extends BaseObject {
    @JsonIgnore
    private RpcRequest rpcRequest;//请求头信息
    private DTO dto;//数据传输对象

    public RpcResponse() {
        super(CODE_SUCC);
    }

    public RpcResponse(String responseCode) {
        super(responseCode);
    }

    public RpcResponse(String responseCode, String responseMessage) {
        super(responseCode, responseMessage);
    }

    public RpcResponse(String responseCode, String responseMessage, Object[] args) {
        super(responseCode, responseMessage, args);
    }

    public RpcResponse(Throwable throwable) {
        super(throwable);
    }

    public RpcResponse(RpcRequest rpcRequest, DTO dto) {
        this.rpcRequest = rpcRequest;
        this.dto = dto;
        if (dto instanceof BaseObject) setErrorInfo((BaseObject) dto);
        if (getResponseCode() == null) setErrorCode(CODE_SUCC);
    }

    public RpcResponse(DTO dto) {
        this(null, dto);
    }

    public RpcResponse(RpcRequest rpcRequest, String responseCode, String responseMessage) {
        super(responseCode, responseMessage);
        this.rpcRequest = rpcRequest;
    }

    public String getResponseCode() {
        return super.getResponseCode();
    }

    public String getResponseMsg() {
        return super.getResponseMsg();
    }

    public String getResponseMessage() {
        return super.getResponseMessage();
    }


    @Override
    public ResponseResult getResponseResult() {
        ResponseResult responseResult = super.getResponseResult();
        return responseResult != null && isNotBlank(responseResult.getCode()) ? responseResult : SUCC;
    }
}
