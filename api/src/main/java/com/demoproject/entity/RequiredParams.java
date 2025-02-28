package com.demoproject.entity;

import com.demoproject.framework.util.xxtea.XXTEA;
import com.demoproject.utils.Libs;
import com.demoproject.utils.Configure;
import java.util.Map;

public class RequiredParams {

    String channel;
    String platformid;
    String user;
    String version;
    String deviceid;
    String token;
    String sign;
    String lang;

    RequiredParams() {
        this.channel = "";
        this.platformid = "";
        this.user = "";
        this.version = "";
        this.deviceid = "";
        this.token = "";
        this.sign = "";
        this.lang = "";
    }

    public String getQuery(final Map<String, String> params) {
        String paramStr = "";
        for (final String key : params.keySet()) {
            paramStr += params.get(key);
        }
        final String strCheck = paramStr + this.lang + this.channel + this.platformid + this.user + this.version + this.deviceid + this.token;
        final String sign_data = XXTEA.encryptToBase64String(strCheck, Configure.API_KEY.getBytes());
        params.put("lang", this.lang);
        params.put("channel", this.channel);
        params.put("platformid", this.platformid);
        params.put("user", this.user);
        params.put("version", this.version);
        params.put("token", this.token);
        params.put("sign", sign_data);
        return Libs.mapToString(params);
    }

    public String getChannel() {
        return this.channel;
    }

    public String getPlatformid() {
        return this.platformid;
    }

    public String getUser() {
        return this.user;
    }

    public String getVersion() {
        return this.version;
    }

    public String getDeviceid() {
        return this.deviceid;
    }

    public String getToken() {
        return this.token;
    }

    public String getSign() {
        return this.sign;
    }

    public String getLang() {
        return this.lang;
    }

    public void setChannel(final String channel) {
        this.channel = channel;
    }

    public void setPlatformid(final String platformid) {
        this.platformid = platformid;
    }

    public void setUser(final String user) {
        this.user = user;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    public void setDeviceid(final String deviceid) {
        this.deviceid = deviceid;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public void setSign(final String sign) {
        this.sign = sign;
    }

    public void setLang(final String lang) {
        this.lang = lang;
    }
}
