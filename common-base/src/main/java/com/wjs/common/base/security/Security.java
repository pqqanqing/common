package com.wjs.common.base.security;

/**
 * Created by panqingqing on 16/8/9.
 */
public abstract class Security {
    protected String key;

    public Security(String key) {
        this.key = key;
    }

    //字符集需要统一,不然不同的字符集加解密,签名结果会不一样
    public abstract String encrypt(String content);

    public abstract String decrypt(String content);

    public abstract String encrypt(String content, String charset);

    public abstract String decrypt(String content, String charset);

}
