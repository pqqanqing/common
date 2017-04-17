package com.wjs.common.base.execption;

import lombok.Getter;
import lombok.Setter;

import static com.wjs.common.base.util.PropertiesUtil.getString;

/**
 * Created by panqingqing on 16/5/3.
 */
public class BusinessExecption extends RuntimeException {

    @Setter
    @Getter
    private String businessCode;//业务信息编码
    @Setter
    @Getter
    private Object[] args;//业务信息参数
    @Setter
    @Getter
    private String msg;//业务信息描述

    public BusinessExecption() {
        super();
    }

    public BusinessExecption(String message) {
        super(getBusinessInfo(message));
        this.msg = getBusinessInfo(message);
        //这里message期望是业务码,尽量不要是直接错误消息!
        this.businessCode = message;
    }

    public BusinessExecption(String message, Throwable cause) {
        super(getBusinessInfo(message), cause);
        this.msg = getBusinessInfo(message);
    }

    public BusinessExecption(Throwable cause) {
        super(cause);
    }

    protected BusinessExecption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.msg = message;
    }

    public BusinessExecption(String errorCode, Object... args) {
        super(getBusinessInfo(errorCode, args));
        this.businessCode = errorCode;
        this.args = args;
        this.msg = getBusinessInfo(errorCode, args);
    }

    private static String getBusinessInfo(String errorCode, Object... args) {
        String result = getString(errorCode) == null ? errorCode : getString(errorCode);
        for (int i = 0; i < args.length; i++) {
            result = result.replace("{" + (i + 1) + "}", (String) args[i]);
        }
        return result;
    }
}
