package com.wjs.common.base.util;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wjs.common.base.constant.Constant.UTF8;
import static java.util.Objects.isNull;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64String;
import static org.apache.commons.lang3.StringUtils.substringBeforeLast;

/**
 * Created by panqingqing on 16/4/6.
 */
public class StringUtil {

    private StringUtil() {
        throw new AssertionError("No cn.com.common.base.util.StringUtil instances for you!");
    }

    public static String doMask(String str, int start, int length) {
        if (isNull(str)) return null;
        int end = start + length;
        if (str.length() < end) throw new RuntimeException("字符串:" + str + "掩码长度不能超过字符串长度:" + str.length());
        String replace = str.substring(start, end);
        String replaceMask = replace.replaceAll(".", "*");
        return str.replaceFirst(replace, replaceMask);
    }

    public static String parseList2String(Object object, String split) {
        if (isNull(object)) return null;
        List<String> list = (List<String>) object;
        StringBuilder result = new StringBuilder();
        for (String str : list) {
            result.append(str + split);
        }
        return substringBeforeLast(result.toString(), split);
    }

    public static String parseMap2String(Object object, String split) {
        if (isNull(object)) return null;
        Map<String, Object> map = (Map<String, Object>) object;
        StringBuilder result = new StringBuilder();
        for (Map.Entry entry : map.entrySet()) {
            result.append(entry.getKey() + "=" + entry.getValue() + split);
        }
        return substringBeforeLast(result.toString(), split);
    }

    public static Map<String, Object> parseString2Map(String result, String split) {
        if (isNull(result)) return null;
        Map<String, Object> map = new HashMap<String, Object>();
        String[] results = StringUtils.split(result, split);
        for (String str : results) {
            int index = str.lastIndexOf("=");
            map.put(str.substring(0, index), str.substring(index + 1));
        }
        return map;
    }

    public static String parseMap2JsonString(Object object) {
        if (isNull(object)) return null;
        Map<String, Object> map = (Map<String, Object>) object;
        return JSONObject.toJSONString(map);
    }

    public static Object parseJsonString2Map(String result) {
        return isNull(result) ? null : JSONObject.parseObject(result, Map.class);
    }

    public static String baseBytes2String(byte[] bytes) {
        return encodeBase64String(bytes);
    }

    public static byte[] baseString2Bytes(String str) {
        return decodeBase64(str);
    }

    public static String bytes2String(byte[] bytes) {
        return bytes2String(bytes, UTF8);
    }

    public static byte[] string2Bytes(String str) {
        return string2Bytes(str, UTF8);
    }

    public static String bytes2String(byte[] bytes, String charset) {
        try {
            return new String(bytes, charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static byte[] string2Bytes(String str, String charset) {
        try {
            return str.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String bytes2HexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        String temp;
        for (int i = 0; i < bytes.length; i++) {
            temp = (Integer.toHexString(bytes[i] & 0xFF));
            if (temp.length() == 1) stringBuilder.append("0");
            stringBuilder.append(temp);
        }
        return stringBuilder.toString();
    }
}