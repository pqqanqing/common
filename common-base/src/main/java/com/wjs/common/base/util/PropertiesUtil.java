package com.wjs.common.base.util;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.wjs.common.base.constant.Constant.*;


/**
 * Created by panqingqing on 16/5/3.
 */
public class PropertiesUtil {

    public static final String ALL_PROPERTIES4_FILE_PATH = "META-INF";
    public static final String PROPERTIES4_TYPE = ".properties";
    public static final String PROPERTIES4_PREFIX = "bc_";

    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private static List<Configuration> configurations = new ArrayList<Configuration>();

    static {
        Set<String> allPropertiesFilePaths = ClassUtil.scanClassPathNameByPackage(ALL_PROPERTIES4_FILE_PATH, PROPERTIES4_TYPE, true);
        for (String file : allPropertiesFilePaths) {
            if (!file.contains(PROPERTIES4_PREFIX)) continue;
            file = file.replace(PROPERTIES4_TYPE, "").replace(POINT, SLASH) + PROPERTIES4_TYPE;
            try {
                //解决中文乱码
                //PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration(file);
                PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();
                propertiesConfiguration.setEncoding(UTF8);
                propertiesConfiguration.load(file);
                configurations.add(propertiesConfiguration);
            } catch (ConfigurationException e) {
                logger.error("绑定资源文件[" + file + "]时发生错误[" + e.getMessage() + "],现已跳过该文件!");
            }
        }
    }

    public static String getString(String key) {
        String result = null;
        for (Configuration configuration : configurations) {
            result = configuration.getString(key);
            if (result != null) break;
        }
        return result;
    }
}
