package com.demoproject.utils;

import com.demoproject.entity.RequiredParams;
import com.demoproject.framework.util.xxtea.XXTEA;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONObject;

public class Libs {

    public static byte flgEncryp;

    public static String mapToString(Map<String, String> map) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (final String key : map.keySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            final String value = map.get(key);
            try {
                stringBuilder.append((key != null) ? URLEncoder.encode(key, "UTF-8") : "");
                stringBuilder.append("=");
                stringBuilder.append((value != null) ? URLEncoder.encode(value, "UTF-8") : "");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("This method requires UTF-8 encoding support", e);
            }
        }
        return stringBuilder.toString();
    }

    public static Map<String, Object> getReturnData(Map<String, Object> data) {
        if ((data.get("msg") == null && data.containsKey("msg")) || !data.containsKey("msg")) {
            data.put("msg", "");
        }
        if (data.get("data") != null || (data.get("data") != null && data.containsKey("data"))) {
            data.put("status", 1);
            String str = "";
            final LinkedHashMap<String, Object> temp = new LinkedHashMap<String, Object>();
            temp.put("cv", data.get("data"));
            final JSONObject jsonTemp = new JSONObject(temp);
            str = jsonTemp.get("cv").toString();
            data.put("data", XXTEA.encryptToBase64String(str, Configure.API_KEY.getBytes()));
        }
        return data;
    }

    public static String cvDataString(Map<String, Object> data) {
        final Map<String, Object> result = getReturnData(data);
        return new JSONObject(result).toString().replace("\\\\", "\\");
    }

    public static String checkParam(RequiredParams params, String... listParam) {
        final String user = params.getUser();
        final String token = params.getToken();
        String sign = params.getSign();
        sign = urlDecode(sign);
        sign = sign.replace(" ", "+");
        final Map<String, Object> data = new HashMap<String, Object>();
        String strCheck = "";
        for (final String param : listParam) {
            strCheck += param;
        }
        strCheck = urlDecode(strCheck);
        strCheck = strCheck + user + token;
        if (!checkSign(strCheck, sign)) {
            data.put("status", 0);
            data.put("msg", WebUtils._l("WRONG_SIGN"));
            return cvDataString(data);
        }
        return "";
    }

    public static boolean checkSign(String str, String signInput) {
        String encrypt_data = XXTEA.decryptBase64StringToString(signInput, Configure.API_KEY.getBytes());
        if (encrypt_data == null) {
            return false;
        }

        encrypt_data = urlDecode(encrypt_data);
        return str.equals(encrypt_data);
    }

    public static String urlDecode(String string) {
        String data = string;
        try {
            data = data.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
            data = data.replaceAll("\\+", "%2B");
            data = URLDecoder.decode(data, "utf-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
}
