package com.demoproject.framework.util;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalINIConfiguration;
import java.io.File;
import java.util.List;
import org.apache.commons.configuration.ConversionException;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.log4j.Logger;

public class ConfigUtil {

    private static final Logger logger;
    private static final String CONFIG_HOME = "conf";
    private static final String CONFIG_FILE = "config.ini";
    private static final CompositeConfiguration config;

    public static String getString(final String section, final String name) {
        final String key = section + "." + name;
        final String value = ConfigUtil.config.getString(key, (String) null);

        return value;
    }

    public static Integer getInteger(final String section, final String name) {
        final String key = section + "." + name;
        Integer value = null;
        try {
            value = ConfigUtil.config.getInteger(key, (Integer) null);
        } catch (ConversionException ex) {
            ConfigUtil.logger.info((Object) "getInteger error", (Throwable) ex);
        }
        return value;
    }

    public static Short getShort(final String section, final String name) {
        final String key = section + "." + name;
        Short value = null;
        try {
            value = ConfigUtil.config.getShort(key, (Short) null);
        } catch (ConversionException ex) {
            ConfigUtil.logger.info((Object) "getShort error", (Throwable) ex);
        }
        return value;
    }

    public static Double getDouble(final String section, final String name) {
        final String key = section + "." + name;
        Double value = null;
        try {
            value = ConfigUtil.config.getDouble(key, (Double) null);
        } catch (ConversionException ex) {
            ConfigUtil.logger.info((Object) "getDouble error", (Throwable) ex);
        }
        return value;
    }

    public static Float getFloat(final String section, final String name) {
        final String key = section + "." + name;
        Float value = null;
        try {
            value = ConfigUtil.config.getFloat(key, (Float) null);
        } catch (ConversionException ex) {
            ConfigUtil.logger.info((Object) "getFloat error", (Throwable) ex);
        }
        return value;
    }

    public static Long getLong(final String section, final String name) {
        final String key = section + "." + name;
        Long value = null;
        try {
            value = ConfigUtil.config.getLong(key, (Long) null);
        } catch (ConversionException ex) {
            ConfigUtil.logger.info((Object) "getLong error", (Throwable) ex);
        }
        return value;
    }

    public static Boolean getBoolean(final String section, final String name) {
        final String key = section + "." + name;
        Boolean value = null;
        try {
            value = ConfigUtil.config.getBoolean(key, (Boolean) null);
        } catch (ConversionException ex) {
            ConfigUtil.logger.info((Object) "getBoolean error", (Throwable) ex);
        }
        return value;
    }

    public static String[] getStringArray(final String section, final String name) {
        final String key = section + "." + name;
        final String[] value = ConfigUtil.config.getStringArray(key);
        return value;
    }

    public static List<Object> getList(final String section, final String name) {
        final String key = section + "." + name;
        List<Object> value = null;
        try {
            value = (List<Object>) ConfigUtil.config.getList(key);
        } catch (ConversionException ex) {
            ConfigUtil.logger.info((Object) "getList error", (Throwable) ex);
        }
        return value;

    }

    static {
        logger = Logger.getLogger(ConfigUtil.class);
        String APP_PATH = System.getProperty("apppath");
        String APP_ENV = System.getProperty("appenv");

        config = new CompositeConfiguration();
        if (StringUtil.isNullOrEmpty(APP_ENV)) {
            APP_ENV = "";
        } else {
            APP_ENV += ".";
        }
        System.out.println("APP_PATH " + APP_PATH + " APP_ENV " + APP_ENV);
        try {
            File configFile = null;
            if (APP_PATH == null || APP_PATH.isEmpty()) {
                configFile = new File("conf" + File.separator + "service.config.ini");
            } else {
                configFile = new File(APP_PATH + File.separator + "conf" + File.separator + APP_ENV + "config.ini");
            }
            config.addConfiguration(new HierarchicalINIConfiguration(configFile), true);
        } catch (ConfigurationException ex) {
            System.out.println("ConfigurationException " + ex.getMessage());
            ConfigUtil.logger.info(ex.getMessage());
            System.exit(1);
        }
    }
}
