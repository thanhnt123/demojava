package com.demoproject.framework.thrift.client;

import com.demoproject.framework.thrift.helper.TConfigException;
import com.demoproject.framework.util.ConvertUtil;
import com.demoproject.framework.util.StringUtil;
import com.demoproject.framework.util.ConfigUtil;
import com.demoproject.framework.util.xxtea.XXTEA;

public class TClientConfig {

    public String host;
    public int port;
    public int timeout;
    public String protocol;
    public boolean framed;
    public boolean ping;
    public int maxActive;
    public int maxIdle;
    public int minIdle;
    public long maxWait;
    public int setMinEvictableIdleTimeMillis;
    public int setTimeBetweenEvictionRunsMillis;
    private String CONFIG_KEY = "demo_2025ss";

    public TClientConfig(final String section) {
        this.CONFIG_KEY = ConvertUtil.toString(ConfigUtil.getString(section, "configkey"), "demo_2025ss");
        String host = ConfigUtil.getString(section, "host");
        final String h = XXTEA.decryptBase64StringToString(host, this.CONFIG_KEY);
        final Integer p = ConvertUtil.toInt(XXTEA.decryptBase64StringToString(ConfigUtil.getString(section, "port"), this.CONFIG_KEY), 0);
        if (StringUtil.isNullOrEmpty(h) || p == null) {
            throw new TConfigException("host or port cannot be null or empty");
        }
        this.host = h;
        this.port = p;
        this.timeout = ConvertUtil.toInt(ConfigUtil.getInteger(section, "timeout"));
        this.protocol = ConvertUtil.toString(ConfigUtil.getString(section, "protocol"), "binary");
        this.framed = ConvertUtil.toBoolean(ConfigUtil.getString(section, "framed"), true);
        this.ping = ConvertUtil.toBoolean(ConfigUtil.getString(section, "ping"), false);
        this.maxActive = ConvertUtil.toInt(ConfigUtil.getInteger(section, "maxactive"), 20);
        this.maxIdle = ConvertUtil.toInt(ConfigUtil.getInteger(section, "maxidle"), 8);
        this.minIdle = ConvertUtil.toInt(ConfigUtil.getInteger(section, "minidle"), 0);
        this.maxWait = ConvertUtil.toLong(ConfigUtil.getInteger(section, "maxwait"), -1L);
        this.setMinEvictableIdleTimeMillis = ConvertUtil.toInt(ConfigUtil.getInteger(section, "set_min_evictable_idle_time_millis"), 30000);
        this.setTimeBetweenEvictionRunsMillis = ConvertUtil.toInt(ConfigUtil.getInteger(section, "set_time_between_eviction_runs_millis"), 30000);
    }
}
