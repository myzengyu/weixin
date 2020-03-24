package com.weixin.common.util;


import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.InputStream;

public class PropertiesUtils {

    private static ResourceLoader resourceLoader = new DefaultResourceLoader();
    private static java.util.Properties props = null;

    static {
        try {
            reload();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private PropertiesUtils() {
    }

    public static void reload() throws Exception {
        if (props == null)
            props = new java.util.Properties();
        String location = "/system.properties";
        InputStream is = null;
        Resource resource = resourceLoader.getResource(location);
        is = resource.getInputStream();
        props.load(is);
        is.close();
    }

    public static String getString(String name) {
        if (props == null)
            return null;
        if (props.getProperty(name) != null)
            return props.getProperty(name).trim();
        return "";
    }

    public static Integer getInteger(String name) {
        String value = getString(name);
        if (value != null && !"".equals(value)) {
            return Integer.parseInt(value);
        }
        return -1;
    }

    public static boolean getBoolean(String name) {
        String value = getString(name);
        if (value != null && !"".equals(value)) {
            return Boolean.parseBoolean(value);
        }
        return false;
    }

}
