package com.tongbu.game.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

/**
 * @author jokin
 * @date 2018/10/28 0028 19:16
 *
 * 获取配置文件信息
 */
public class PropertiesUtils {
    private Properties props = new Properties();
    private InputStream inputStream = null;

    public PropertiesUtils(String fileName) {
        try {
            this.inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName);
            this.props.load(this.inputStream);
        } catch (Exception var3) {
            var3.printStackTrace();
        }
    }

    public String getString(String key) {
        return this.props.getProperty(key);
    }

    public String getString(String key, String defaultValue) {
        return this.props.getProperty(key, defaultValue);
    }

    public Boolean getBoolean(String key) {
        String var1 = this.props.getProperty(key);
        return var1 == null ? false : Boolean.valueOf(var1);
    }

    public Boolean getBoolean(String key, Boolean defaultValue) {
        String var1 = this.props.getProperty(key);
        return var1 == null ? defaultValue : Boolean.valueOf(var1);
    }

    public Integer getInteger(String key) {
        String val = this.props.getProperty(key);
        return val == null ? null : Integer.parseInt(val);
    }

    public Integer getInteger(String key, Integer defaultValue) {
        String val = this.props.getProperty(key);
        return val == null ? defaultValue : Integer.parseInt(val);
    }

    public Object set(String key,String value){
        return this.props.setProperty(key,value);
    }

    public void close()
    {
        try {
            this.inputStream.close();
        } catch (IOException ignored) {
        }
    }

    public Set stringPropertyNames() {
        return this.props.stringPropertyNames();
    }
}
