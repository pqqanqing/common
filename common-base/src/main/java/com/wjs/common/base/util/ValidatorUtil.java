package com.wjs.common.base.util;


import com.wjs.common.base.validation.ValidationResult;
import org.apache.commons.collections.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.regex.Pattern.matches;

/**
 * Created by panqingqing on 15/12/15.
 */
public class ValidatorUtil {

    //验证手机号
    public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    //验证邮箱
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    //验证汉字
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";
    //验证身份证
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
    //验证URL
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
    //验证IP地址
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
    //validator
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> ValidationResult validateEntity(T object) {
        ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<T>> validates = validator.validate(object, Default.class);
        Map<String, String> errorMsg = new HashMap<String, String>();
        if (CollectionUtils.isNotEmpty(validates)) {
            result.setHasErrors(true);
            for (ConstraintViolation<T> cv : validates) {
                errorMsg.put(cv.getPropertyPath().toString(), cv.getMessage());
            }
            result.setErrorMsg(errorMsg);
        }
        return result;
    }

    public static boolean isMatchRegex(String content, String regex) {
        return matches(regex, content);
    }

    public static void main(String[] args) {
        ValidationResult validationResult = validateEntity(new Object());
        if (validationResult.isHasErrors()) throw new RuntimeException(validationResult.getErrorMsg().toString());
    }

}
