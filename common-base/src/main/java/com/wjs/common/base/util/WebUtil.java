package com.wjs.common.base.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Created by panqingqing on 16/12/9.
 */
public class WebUtil {

    //参考网站:https://my.oschina.net/nyp/blog/415956
    public static Map<String, Object> transToMAP(Map parameterMap) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Iterator entries = parameterMap.entrySet().iterator();
        String value = "";
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (isNull(valueObj)) {
                value = null;
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            if (isBlank(value)) continue;
            returnMap.put(name, value);
        }
        return returnMap;
    }
}
