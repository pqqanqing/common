package com.wjs.common.base.base;

import com.wjs.common.base.execption.BusinessExecption;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author panqingqing
 * @title 响应结果
 * Created by panqingqing on 16/6/20.
 */
@Setter
@Getter
@Slf4j
public class ResponseResult implements Serializable {

    //这样的代码很脆弱,如果它被重构进超类或子类的话,显示声明类名就会有问题.可以用java7的新特性解决问题.
    //private Logger logger = LoggerFactory.getLogger(ResponseResult.class);

    //应答码
    public static final String CODE_SUCC = "000";//（最终）成功
    public static final String CODE_ING = "111";//处理中或请求成功（需要异步通知）
    public static final String CODE_ERROR = "999";//系统错误
    //应答码
    public static final String RESULT_SUCC = "SUCC";//（最终）成功
    public static final String RESULT_ING = "ING";//处理中或请求成功（需要异步通知）
    public static final String RESULT_FAIL = "FAIL";//处理失败
    public static final String RESULT_ERROR = "ERROR";//系统错误
    //常量
    public static final ResponseResult SUCC = new ResponseResult(CODE_SUCC);

    private String code;//业务信息编码
    private String msg;//业务信息描述
    private Object[] args;//业务信息参数
    private String message;//业务异常消息

    public ResponseResult() {
    }

    public ResponseResult(String code) {
        this.code = code;
    }

    public ResponseResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(String code, String msg, Object[] args) {
        this.code = code;
        this.msg = msg;
        this.args = args;
    }

    public ResponseResult(Throwable throwable) {
        //是否是业务异常
        if (throwable instanceof BusinessExecption) {
            BusinessExecption businessExecption = (BusinessExecption) throwable;
            this.code = businessExecption.getBusinessCode();
            this.args = businessExecption.getArgs();
            this.msg = businessExecption.getMsg();
            this.message = businessExecption.getMessage();
        } else {
            this.code = CODE_ERROR;
            this.message = throwable.getMessage() == null ? throwable.toString() : throwable.getMessage();
            log.info("异常信息:" + throwable);
        }
    }

    protected boolean isSuccess() {
        return CODE_SUCC.equals(code);
    }

    protected boolean isUnknow() {
        return CODE_ERROR.equals(code);
    }

    protected boolean isError() {
        return CODE_ERROR.equals(code);
    }

    protected boolean isIng() {
        return CODE_ING.equals(code);
    }

    protected boolean isFail() {
        return code != null && !isSuccess() && !isIng() && !isUnknow() && !isError();
    }

    protected boolean isFinish() {
        return isSuccess() || isFail();
    }

    public String getResult() {
        if (isSuccess()) return RESULT_SUCC;
        if (isIng()) return RESULT_ING;
        if (isError()) return RESULT_ERROR;
        return RESULT_FAIL;
    }

    @Override
    public String toString() {
        return String.format("%s对应的%s对应的异常描述:%s", code, msg, message);
    }
}
