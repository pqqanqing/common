package com.wjs.common.base.interceptor;

import com.wjs.common.base.rpc.RpcResponse;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Created by panqingqing on 16/8/25.
 */
public class FacadeInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        try {
            return methodInvocation.proceed();
        } catch (Throwable e) {
            return new RpcResponse(e);
        }
    }
}
