package com.demoproject.utils;

import com.demoproject.entity.RequiredParams;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class WebUtils {

    private static HttpServletRequest request;
    static MessageSource messages;
    static String lang;
    static RequiredParams requiredParams;

    public static String getLang() {
        return LocaleContextHolder.getLocale().toLanguageTag();
    }

    public static String _l(String text) {
        if (WebUtils.messages == null) {
            ResourceBundleMessageSource source = new ResourceBundleMessageSource();
            source.setBasenames(new String[]{"lang/msg"});
            source.setDefaultEncoding("UTF-8");
            source.setUseCodeAsDefaultMessage(true);
            WebUtils.messages = source;
        }
        String str = WebUtils.messages.getMessage(text, null, LocaleContextHolder.getLocale());
        return str;
    }

    public static Map<String, String[]> getParams() {
        return (Map<String, String[]>) WebUtils.request.getParameterMap();
    }

    public static String getClientIpAddr() {
        if (WebUtils.request == null) {
            return "";
        }
        String ip = WebUtils.request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = WebUtils.request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = WebUtils.request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = WebUtils.request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = WebUtils.request.getHeader("HTTP_X_FORWARDED");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = WebUtils.request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = WebUtils.request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = WebUtils.request.getHeader("HTTP_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = WebUtils.request.getHeader("HTTP_FORWARDED");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = WebUtils.request.getHeader("HTTP_VIA");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = WebUtils.request.getHeader("REMOTE_ADDR");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = WebUtils.request.getRemoteAddr();
        }
        final String[] ipstr = ip.split(",");
        return ipstr[0];
    }
}
