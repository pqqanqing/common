package com.wjs.common.base.security;

/**
 * Created by panqingqing on 16/8/10.
 */
public abstract class Signature {

    protected String key;

    public Signature(String key) {
        this.key = key;
    }

    public abstract String digest(String content);

    public abstract String digest(String content, String charset);

    //可以参考用DigestUtils这个工具类,里面提供了很多签名摘要方法

}