package com.wjs.common.base.interceptor;

import com.wjs.common.base.rpc.RpcResponse;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Created by panqingqing on 16/8/25.
 */
@Slf4j
public class FacadeInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        try {
            return methodInvocation.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
            log.info("异常信息如下:", e);
            return new RpcResponse(e);
        }
    }
}
