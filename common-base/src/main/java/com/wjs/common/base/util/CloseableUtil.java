package com.wjs.common.base.util;

/**
 * Created by panqingqing on 16/1/11.
 */
public class CloseableUtil {

    public static void close(AutoCloseable... autoCloseables) {
        for (AutoCloseable autoCloseable : autoCloseables) {
            if (null != autoCloseable) {
                try {
                    autoCloseable.close();
                } catch (Exception e) {
                    throw new RuntimeException("关闭资源" + e.getMessage() + "异常", e);
                } finally {
                    autoCloseable = null;
                }
            }
        }
    }
}
