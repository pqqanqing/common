package com.wjs.common.base.validation;

import java.util.Map;

/**
 * Created by panqingqing on 15/12/15.
 */
public class ValidationResult {

    private boolean hasErrors;//校验结果是否有错
    private Map<String, String> errorMsg; //校验错误信息

    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public Map<String, String> getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(Map<String, String> errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "ValidationResult [hasErrors=" + hasErrors + ", errorMsg="
                + errorMsg + "]";
    }
}
