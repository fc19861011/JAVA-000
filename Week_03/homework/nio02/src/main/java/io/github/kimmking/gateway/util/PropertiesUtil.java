package io.github.kimmking.gateway.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
    private static final String FILE_PATH = "config.properties";

    /**
     * 通过传入的路径及key，获得对应的值
     * @param path
     * @param key
     * @return
     */
    public static String getValue(String path, String key)
    {
        Properties properties = new Properties();
        try
        {
            properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(path));
        }
        catch (IOException e)
        {
            throw new RuntimeException("File Read Failed...", e);
        }
        return properties.getProperty(key);
    }

    /**
     * 通过key直接获取对应的值
     * @param key
     * @return
     */
    public static String getValue(String key)
    {
        Properties properties = new Properties();
        try
        {
            properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(FILE_PATH));
        }
        catch (IOException e)
        {
            throw new RuntimeException("File Read Failed...", e);
        }
        return properties.getProperty(key);
    }
}
