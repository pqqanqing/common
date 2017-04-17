package com.wjs.common.base.security;

/**
 * Created by panqingqing on 16/8/9.
 */
public class SecurityKey {
    private String key;
    private String value;
    private Security security;
    private Signature signature;

    public SecurityKey() {

    }

    public SecurityKey(String key, String value) {
        this.key = key;
        this.value = value;
        this.security = new AesSecurity(value);
        this.signature = new Sha256Signature(value);
    }

    public String encrypt(String content) {
        return security.encrypt(content);
    }

    public String decrypt(String content) {
        return security.decrypt(content);
    }

    public String digest(String content) {
        return signature.digest(content);
    }

    public String getKey() {
        return key;
    }
}
