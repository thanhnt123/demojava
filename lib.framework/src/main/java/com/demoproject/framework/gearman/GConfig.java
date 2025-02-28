package com.demoproject.framework.gearman;

import com.demoproject.framework.util.ConfigUtil;
import com.demoproject.framework.util.ConvertUtil;
import com.demoproject.framework.util.StringUtil;
import com.demoproject.framework.util.xxtea.XXTEA;

public class GConfig {

    public String host;
    public int port;
    public String function;
    public int maxActive;
    public int maxIdle;
    public int minIdle;
    public long maxWait;
    public int setMinEvictableIdleTimeMillis;
    public int setTimeBetweenEvictionRunsMillis;
    public String CONFIG_KEY = "demo_2025ss";

    public GConfig(final String section) {
        try {
            this.CONFIG_KEY = ConvertUtil.toString(ConfigUtil.getString(section, "configkey"), "demo_2025ss");
            final String h = XXTEA.decryptBase64StringToString(ConfigUtil.getString(section, "host"), this.CONFIG_KEY);
            final Integer p = ConvertUtil.toInt(XXTEA.decryptBase64StringToString(ConfigUtil.getString(section, "port"), this.CONFIG_KEY), 0);
            final String fn = ConfigUtil.getString(section, "function");
            if (StringUtil.isNullOrEmpty(h) || p == null) {
                throw new RuntimeException("host or port cannot be null or empty");
            }
            this.host = h;
            this.port = p;
            this.function = fn;
            this.maxActive = ConvertUtil.toInt((Object) ConfigUtil.getInteger(section, "maxactive"), 20);
            this.maxIdle = ConvertUtil.toInt((Object) ConfigUtil.getInteger(section, "maxidle"), 8);
            this.minIdle = ConvertUtil.toInt((Object) ConfigUtil.getInteger(section, "minidle"), 0);
            this.maxWait = ConvertUtil.toInt((Object) ConfigUtil.getInteger(section, "maxwait"), -1);
            this.setMinEvictableIdleTimeMillis = ConvertUtil.toInt((Object) ConfigUtil.getInteger(section, "set_min_evictable_idle_time_millis"), 30000);
            this.setTimeBetweenEvictionRunsMillis = ConvertUtil.toInt((Object) ConfigUtil.getInteger(section, "set_time_between_eviction_runs_millis"), 30000);
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
