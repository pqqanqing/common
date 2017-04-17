package com.wjs.common.base.properties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by panqingqing on 16/7/11.
 */
public class ProjectProperties {

    private static Map<String, String> properties = new HashMap();

    public ProjectProperties() {
    }

    public static String getProperty(String key) {
        return properties.get(key);
    }

    public void setProperties(Map<String, String> p) {
        properties.putAll(p);
    }
}
