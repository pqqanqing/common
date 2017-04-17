package com.wjs.common.base.util;

import static java.lang.String.valueOf;

/**
 * Created by panqingqing on 16/8/19.
 */
public class RandomUtil {

    public static int generateFour() {
        return generateRandomNumber(4);
    }

    public static int generateSix() {
        return generateRandomNumber(6);
    }

    public static String generateFour4String() {
        return valueOf(generateFour());
    }

    public static String generateSix4String() {
        return valueOf(generateSix());
    }

    public static int generateRandomNumber(int digit) {
        int multiply = 9;
        int add = 1;
        for (int i = 1; i < digit; i++) {
            add = add * 10;
        }
        multiply = multiply * add;
        double result = Math.random() * multiply + add;
        return (int) result;
    }
}