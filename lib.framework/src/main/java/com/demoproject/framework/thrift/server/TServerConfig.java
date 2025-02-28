package com.demoproject.framework.thrift.server;

import com.demoproject.framework.util.ConvertUtil;
import com.demoproject.framework.thrift.helper.TConfigException;
import com.demoproject.framework.util.StringUtil;
import com.demoproject.framework.util.ConfigUtil;
import com.demoproject.framework.util.xxtea.XXTEA;

public class TServerConfig {

    public String mode;
    public String host;
    public int port;
    public int maxThread;
    public int minThread;
    public String protocol;
    public boolean framed;
    public int maxReadBufferBytes;
    private String CONFIG_KEY = "demo_2025ss";

    public TServerConfig(final String section) {
        this.CONFIG_KEY = ConvertUtil.toString(ConfigUtil.getString(section, "configkey"), CONFIG_KEY);
        final String m = ConfigUtil.getString(section, "mode");
        if (StringUtil.isNullOrEmpty(m)) {
            throw new TConfigException("mode cannot be null or empty");
        }
        final String h = XXTEA.decryptBase64StringToString(ConfigUtil.getString(section, "host"), this.CONFIG_KEY);
        final Integer p = ConvertUtil.toInt(XXTEA.decryptBase64StringToString(ConfigUtil.getString(section, "port"), this.CONFIG_KEY));
        if (StringUtil.isNullOrEmpty(h) || p == null) {
            throw new TConfigException("host or port cannot be null or empty");
        }
        this.mode = m;
        this.host = h;
        this.port = p;
        this.protocol = ConvertUtil.toString(ConfigUtil.getString(section, "protocol"), "binary");
        this.framed = ConvertUtil.toBoolean(ConfigUtil.getString(section, "framed"), true);
        this.maxThread = ConvertUtil.toInt(ConfigUtil.getInteger(section, "maxthread"), 1024);
        this.minThread = ConvertUtil.toInt(ConfigUtil.getInteger(section, "minthread"), 16);
        this.maxReadBufferBytes = ConvertUtil.toInt(ConfigUtil.getInteger(section, "maxReadBufferBytes"), 128 * 1024 * 1024);
    }
}
