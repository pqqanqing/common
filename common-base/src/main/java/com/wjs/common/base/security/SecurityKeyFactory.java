package com.wjs.common.base.security;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.wjs.common.base.constant.Constant.UTF8;
import static java.util.Objects.isNull;

/**
 * Created by panqingqing on 16/8/9.
 */
public class SecurityKeyFactory {

    private static SecurityKeyFactory defaultSecurityKeyFactory = new SecurityKeyFactory("/opt/security/key.properties");
    private Map<String, SecurityKey> securityKeyMap = new HashMap<String, SecurityKey>();
    private PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();

    public SecurityKeyFactory(String path) {
        File file = new File(path);
        if (!file.exists()) return;
        loadPath(path);
        assembleSecurityKeyMap();
    }

    public static SecurityKey getSecurityKey(String key) {
        return isNull(key) ? null : defaultSecurityKeyFactory.securityKeyMap.get(key);
    }

    private void loadPath(String path) {
        propertiesConfiguration.setEncoding(UTF8);
        try {
            propertiesConfiguration.load(path);
        } catch (ConfigurationException e) {
            throw new RuntimeException("绑定资源文件[" + path + "]时发生错误[" + e.getMessage() + "],现已跳过该文件!");
        }
    }

    private void assembleSecurityKeyMap() {
        Iterator<String> keys = propertiesConfiguration.getKeys();
        while (keys.hasNext()) {
            String key = keys.next();
            securityKeyMap.put(key, new SecurityKey(key, propertiesConfiguration.getString(key)));
        }
    }

}
