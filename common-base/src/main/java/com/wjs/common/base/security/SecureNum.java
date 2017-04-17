package com.wjs.common.base.security;

import com.wjs.common.base.util.StringUtil;
import lombok.Getter;
import lombok.Setter;

import static java.util.Objects.isNull;

/**
 * Created by panqingqing on 16/8/9.
 */
@Setter
@Getter
public class SecureNum {
    private String plainText;//明文
    private String mask;//掩码
    private String digest;//摘要
    private String cipherText;//密文
    private SecurityKey securityKey;//安全key

    public SecureNum() {
    }

    public SecureNum(String plainText, SecurityKey securityKey) {
        if (isNull(plainText)) return;
        this.plainText = plainText;
        this.securityKey = securityKey;
        this.digest = digest();
        this.cipherText = encrypt();
    }

    public SecureNum(String plainText, int start, int length, SecurityKey securityKey) {
        this(plainText, securityKey);
        this.mask = StringUtil.doMask(plainText, start, length);
    }

    public String encrypt() {
        return securityKey.encrypt(plainText);
    }

    public String decrypt() {
        return securityKey.decrypt(cipherText);
    }

    public String digest() {
        return securityKey.digest(plainText);
    }

}
