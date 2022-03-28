package org.apache.dubbo.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.dubbo.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author amazinglogic
 * @date 2022/3/28
 */
public class ApiVersionConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiVersionConfig.class);

    private static final String API_VERSION_CONFIG_FILE_NAME = "router.properties";
    private static final String API_VERSION_PROP_NAME = "api.version";
    private static final String DEFAULT_VERSION = "0";
    private static final String API_VERSION = loadApiVersion();

    public static String getApiVersion() {
        return API_VERSION;
    }

    private static String loadApiVersion() {
        Properties props = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = null;
        if (classLoader != null) {
            is = classLoader.getResourceAsStream(API_VERSION_CONFIG_FILE_NAME);
        }

        if (is == null) {
            classLoader = ApiVersionConfig.class.getClassLoader();
            if (classLoader != null) {
                is = classLoader.getResourceAsStream(API_VERSION_CONFIG_FILE_NAME);
            }
        }

        if (is != null) {
            try {
                props.load(is);
            }
            catch (IOException e) {
                LOGGER.error("router.properties load error", e);
            }
        }
        String version = props.getProperty(API_VERSION_PROP_NAME);
        if (!StringUtils.isBlank(version)) {
            LOGGER.info("apiVersion: {}", version);
        }
        else {
            LOGGER.info("default apiVersion : {}", DEFAULT_VERSION);
            version = DEFAULT_VERSION;
        }
        return version;
    }
}
