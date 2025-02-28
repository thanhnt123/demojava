package com.demoproject.helper;

import java.util.HashMap;
import java.util.Map;

public class ConfigHelper {

    protected String version;
    protected String imei;
    protected String platform;
    protected String token;

    public ConfigHelper(String version, String imei, String platform, String token) {
        this.token = "";
        this.version = version;
        this.imei = imei;
        this.platform = platform;
        this.token = token;
    }

    public Map<String, Object> dataReturn() {
        final Map<String, Object> data = new HashMap<>();
        final Map<String, Object> mapConfig = new HashMap<>();

        data.put("status", 1);
        data.put("data", mapConfig);
        return data;
    }
}
