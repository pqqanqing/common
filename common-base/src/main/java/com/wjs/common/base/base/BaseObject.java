package com.wjs.common.base.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wjs.common.base.util.ClassUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 所有自定义类型的基础类
 *
 * @author panqingqing
 * @description 该类可以提供对象信息输出/系统日志输出等公用功能
 * Created by panqingqing on 16/6/20.
 */
@Setter
@Getter
public class BaseObject implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    @JsonIgnore
    protected ResponseResult responseResult;//响应结果

    public BaseObject() {
    }

    public BaseObject(String responseCode) {
        responseResult = new ResponseResult(responseCode);
    }

    public BaseObject(String responseCode, String responseMsg) {
        responseResult = new ResponseResult(responseCode, responseMsg);
    }

    public BaseObject(String responseCode, String responseMsg, Object[] args) {
        responseResult = new ResponseResult(responseCode, responseMsg, args);
    }

    public BaseObject(Throwable throwable) {
        responseResult = new ResponseResult(throwable);
    }

    protected void setErrorInfo(BaseObject other) {
        responseResult = other.getResponseResult();
    }

    protected String getErrorCode() {
        return responseResult != null ? responseResult.getCode() : null;
    }

    protected void setErrorCode(String errorCode) {
        responseResult = new ResponseResult(errorCode);
    }

    protected String getErrorMsg() {
        return responseResult != null ? responseResult.getMsg() : null;
    }

    protected String getErrorMessage() {
        return responseResult != null ? responseResult.getMessage() : null;
    }

    protected Object[] getErrorArgs() {
        return responseResult != null ? responseResult.getArgs() : null;
    }

    protected boolean isSuccess() {
        return responseResult != null && responseResult.isSuccess();
    }

    protected boolean isUnknow() {
        return responseResult != null && responseResult.isUnknow();
    }

    protected boolean isError() {
        return responseResult != null && responseResult.isError();
    }

    protected boolean isIng() {
        return responseResult != null && responseResult.isIng();
    }

    protected boolean isFail() {
        return responseResult != null && responseResult.isFail();
    }

    protected boolean isFinish() {
        return responseResult != null && responseResult.isFinish();
    }

    protected String getResult() {
        return responseResult != null ? responseResult.getResult() : null;
    }

    protected String getResponseCode() {
        return getErrorCode();
    }

    protected String getResponseMsg() {
        return responseResult != null ? responseResult.getMsg() : null;
    }

    protected String getResponseMessage() {
        return responseResult != null ? responseResult.getMessage() : null;
    }

    protected Object[] getResponseArgs() {
        return responseResult != null ? responseResult.getArgs() : null;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return ClassUtil.equals(this, obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
