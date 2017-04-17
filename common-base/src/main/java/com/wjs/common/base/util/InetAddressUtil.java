package com.wjs.common.base.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by panqingqing on 16/7/22.
 */
public class InetAddressUtil {

    public static InetAddress getInetAddress() {
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException("未知的地址信息:" + e.getMessage(), e);
        }
    }

    public static byte[] getAddress() {
        return getInetAddress().getAddress();
    }

    public static String getHostAddress() {
        return getInetAddress().getHostAddress();
    }

    public static String getHostName() {
        return getInetAddress().getHostName();
    }

    public static String getCanonicalHostName() {
        return getInetAddress().getCanonicalHostName();
    }
}
