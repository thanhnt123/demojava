package com.demoproject.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.TimeZone;

public class Configure {

    public static Properties prop;
    public static String API_KEY;
    public static String CONFIG_KEY;
    public static String PORT;
    public static String TIME_ZONE;

    public void getPropeties() {
        InputStream input = null;
        try {
            input = getClass().getClassLoader().getResourceAsStream("application.properties");
            prop.load(input);
            Configure.prop.load(input);
            input.close();
            Configure.CONFIG_KEY = (Configure.prop.containsKey("key.CONFIG_KEY") ? Configure.prop.getProperty("key.CONFIG_KEY") : CONFIG_KEY);
            Configure.API_KEY = (Configure.prop.containsKey("key.API_KEY") ? Configure.prop.getProperty("key.API_KEY") : API_KEY);
            if (Configure.prop.containsKey("api.port")) {
                Configure.PORT = Configure.prop.getProperty("api.port");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void reload() {
        getPropeties();
        TimeZone.setDefault(TimeZone.getTimeZone(Configure.TIME_ZONE));
        System.out.println(TimeZone.getDefault());
    }

    static {
        Configure.TIME_ZONE = "GMT+7:00";
        Configure.prop = new Properties();
        new Configure().reload();
    }
}
