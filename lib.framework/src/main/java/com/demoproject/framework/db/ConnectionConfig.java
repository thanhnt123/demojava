package com.demoproject.framework.db;

import com.demoproject.framework.util.ConfigUtil;
import com.demoproject.framework.util.ConvertUtil;
import com.demoproject.framework.util.xxtea.XXTEA;

public class ConnectionConfig {

    public String host;
    public String port;
    public String dbname;
    public String driver;
    public String url;
    public String user;
    public String password;
    public String timezone;
    public int maxActive;
    public int maxIdle;
    public int minIdle;
    public long maxWait;
    public int setMinEvictableIdleTimeMillis;
    public int setTimeBetweenEvictionRunsMillis;
    public String CONFIG_KEY = "demo_2025ss";

    public ConnectionConfig(final String section) {
        this.CONFIG_KEY = ConvertUtil.toString(ConfigUtil.getString(section, "configkey"), CONFIG_KEY);
        this.driver = ConvertUtil.toString(ConfigUtil.getString(section, "driver"), "com.mysql.jdbc.Driver");
        this.host = XXTEA.decryptBase64StringToString(ConvertUtil.toString(ConfigUtil.getString(section, "host")), this.CONFIG_KEY);
        this.port = XXTEA.decryptBase64StringToString(ConvertUtil.toString(ConfigUtil.getString(section, "port")), this.CONFIG_KEY);
        this.dbname = XXTEA.decryptBase64StringToString(ConvertUtil.toString(ConfigUtil.getString(section, "dbname")), this.CONFIG_KEY);
        this.timezone = ConvertUtil.toString(ConfigUtil.getString(section, "timezone"), "serverTimezone=UTC");
        this.url = String.format("jdbc:mysql://%s:%s/%s?autoReconnectForPools=true&useUnicode=true&characterEncoding=utf-8&%s", this.host, this.port, this.dbname, this.timezone);
        this.user = XXTEA.decryptBase64StringToString(ConvertUtil.toString(ConfigUtil.getString(section, "user"), ""), this.CONFIG_KEY);
        this.password = XXTEA.decryptBase64StringToString(ConvertUtil.toString(ConfigUtil.getString(section, "password"), ""), this.CONFIG_KEY);
        this.maxActive = ConvertUtil.toInt(ConfigUtil.getInteger(section, "maxactive"), 20);
        this.maxIdle = ConvertUtil.toInt(ConfigUtil.getInteger(section, "maxidle"), 8);
        this.minIdle = ConvertUtil.toInt(ConfigUtil.getInteger(section, "minidle"), 0);
        this.maxWait = ConvertUtil.toLong(ConfigUtil.getInteger(section, "maxwait"), -1L);
        this.setMinEvictableIdleTimeMillis = ConvertUtil.toInt(ConfigUtil.getInteger(section, "set_min_evictable_idle_time_millis"), 30000);
        this.setTimeBetweenEvictionRunsMillis = ConvertUtil.toInt(ConfigUtil.getInteger(section, "set_time_between_eviction_runs_millis"), 30000);
        System.out.println("this.dbname " + this.dbname);
        System.out.println("this.user " + this.user);
        System.out.println("this.url " + this.url);
    }
}
