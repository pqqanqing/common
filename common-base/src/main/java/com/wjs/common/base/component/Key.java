package com.wjs.common.base.component;

import lombok.Getter;
import lombok.Setter;

import static java.util.Objects.nonNull;

/**
 * Created by panqingqing on 16/8/29.
 */
@Setter
@Getter
public class Key {

    private static final String SECRET_KEY = "4567gbunkl;'sadasd";

    private String secretKey;
    private String publicKey;
    private String privateKey;

    public Key() {
    }

    public String getSecretKey() {
        return nonNull(secretKey) ? secretKey : SECRET_KEY;
    }

}
