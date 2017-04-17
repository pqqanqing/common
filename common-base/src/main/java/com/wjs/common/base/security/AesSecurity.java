package com.wjs.common.base.security;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

import static com.wjs.common.base.constant.Constant.UTF8;
import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

/**
 * Created by panqingqing on 16/8/9.
 */
public class AesSecurity extends Security {

    private final static String AES = "AES";
    private final static String ALGORITHM_PATTERN_COMPLEMENT = "AES/ECB/PKCS5Padding";//算法/模式/补码方式
    private SecretKeySpec secretKeySpec;

    public AesSecurity(String key) {
        super(key);
        //保证加解密都是用同一个SecretKeySpec
        this.secretKeySpec = generateSecretKeySpec();
    }

    private SecretKeySpec generateSecretKeySpec() {
        KeyGenerator keyGenerator = null;
        SecureRandom secureRandom = null;
        try {
            keyGenerator = KeyGenerator.getInstance(AES);
            secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes(UTF8));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        keyGenerator.init(128, secureRandom);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] encoded = secretKey.getEncoded();
        return new SecretKeySpec(encoded, AES);
    }

    @Override
    public String encrypt(String content) {
        return encrypt(content, UTF8);
    }

    @Override
    public String decrypt(String content) {
        return decrypt(content, UTF8);
    }

    @Override
    public String encrypt(String content, String charset) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_PATTERN_COMPLEMENT);
            cipher.init(ENCRYPT_MODE, secretKeySpec);
            byte[] bytes = cipher.doFinal(content.getBytes(charset));
            return new Base64().encodeToString(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public String decrypt(String content, String charset) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_PATTERN_COMPLEMENT);
            cipher.init(DECRYPT_MODE, secretKeySpec);
            byte[] decode = new Base64().decode(content);
            byte[] original = cipher.doFinal(decode);
            return new String(original, charset);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
