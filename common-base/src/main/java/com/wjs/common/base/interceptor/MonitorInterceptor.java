package com.wjs.common.base.interceptor;

import com.wjs.common.base.execption.BusinessExecption;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

import static java.util.UUID.randomUUID;

/**
 * Created by panqingqing on 16/7/31.
 */
public class MonitorInterceptor implements MethodInterceptor {

    private static Logger logger = LoggerFactory.getLogger(MonitorInterceptor.class);

    private String methodName;
    private long startTime;
    private long endTime;

    @PostConstruct
    public void init() {
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        invokeBefore();
        try {
            this.methodName = methodInvocation.getMethod().getName();
            return methodInvocation.proceed();
        } catch (Throwable e) {
            if (!(e instanceof BusinessExecption)) callPolice(e);
            throw e;
        } finally {
            invokeAfter();
        }
    }

    private void invokeBefore() {
        this.startTime = System.currentTimeMillis();
    }

    private void callPolice(Throwable e) {
        // TODO: 16/7/31 报警.发邮件或者发短信
    }

    private void invokeAfter() {
        this.endTime = System.currentTimeMillis();
        logger.debug("唯一标识:" + randomUUID().toString() + ",方法名:" + methodName + ",处理时间:" + getProcessTime());
    }

    private Long getProcessTime() {
        return (endTime - startTime) / 1000;
    }
}
