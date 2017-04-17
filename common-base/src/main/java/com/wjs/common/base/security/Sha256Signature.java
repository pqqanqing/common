package com.wjs.common.base.security;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

import static com.wjs.common.base.constant.Constant.UTF8;

/**
 * Created by panqingqing on 16/8/10.
 */
public class Sha256Signature extends Signature {
    private final static String SHA_256 = "SHA-256";

    public Sha256Signature(String key) {
        super(key);
    }

    @Override
    public String digest(String content) {
        return digest(content, UTF8);
    }

    @Override
    public String digest(String content, String charset) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(SHA_256);
            messageDigest.update((content + key).getBytes(charset));
            byte[] digest = messageDigest.digest();
            return new Base64().encodeToString(digest);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}