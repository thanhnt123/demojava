package com.demoproject.framework.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidUtil {

    private static final Pattern URL_PATTERN;
    private static final Pattern EMAIL_PATTERN;
    private static final Pattern NUMBER_PATTERN;
    private static final Pattern USERNAME_PATTERN;
    private static final Pattern IMAGE_EXT_PATTERN;
    private static final Pattern IMAGE_CONTENT_TYPE_PATTERN;

    public static boolean isUrl(final String url) {
        if (StringUtil.isNullOrEmpty(url)) {
            return false;
        }
        final Matcher match = ValidUtil.URL_PATTERN.matcher(url);
        return match.find();
    }

    public static boolean isEmail(final String email) {
        if (StringUtil.isNullOrEmpty(email)) {
            return false;
        }
        final Matcher match = ValidUtil.EMAIL_PATTERN.matcher(email);
        return match.find();
    }

    public static boolean isNumber(final String number) {
        if (StringUtil.isNullOrEmpty(number)) {
            return false;
        }
        final Matcher match = ValidUtil.NUMBER_PATTERN.matcher(number);
        return match.find();
    }

    public static boolean isUserName(final String username) {
        if (StringUtil.isNullOrEmpty(username)) {
            return false;
        }
        final Matcher match = ValidUtil.USERNAME_PATTERN.matcher(username);
        return match.find();
    }

    public static boolean isImageExt(final String ext) {
        if (StringUtil.isNullOrEmpty(ext)) {
            return false;
        }
        final Matcher match = ValidUtil.IMAGE_EXT_PATTERN.matcher(ext);
        return match.find();
    }

    public static boolean isImageContentType(final String contentType) {
        if (StringUtil.isNullOrEmpty(contentType)) {
            return false;
        }
        final Matcher match = ValidUtil.IMAGE_CONTENT_TYPE_PATTERN.matcher(contentType);
        return match.find();
    }

    public static boolean isGender(final String gender) {
        return !StringUtil.isNullOrEmpty(gender) && (gender.equals("M") || gender.equals("F") || gender.equals("X"));
    }

    public static boolean isNullOrEmpty(final Object data) {
        return data == null || "".equals(data.toString());
    }

    public static boolean isInArray(final Object item, final Object[] items) {
        return isInArray(item, items, false);
    }

    public static boolean isInArray(final Object item, final Object[] items, final boolean ignoreCase) {
        if (items == null || items.length == 0) {
            return false;
        }
        boolean result = false;
        for (final Object tmp : items) {
            if (tmp != null) {
                if (item != null) {
                    if (tmp instanceof String || item instanceof String) {
                        if (((String) tmp).equals(item) || (ignoreCase && ((String) tmp).equalsIgnoreCase((String) item))) {
                            result = true;
                            break;
                        }
                    } else if (tmp == item || tmp.equals(item)) {
                        result = true;
                        break;
                    }
                }
            } else if (item == null) {
                result = true;
                break;
            }
        }
        return result;
    }

    public static boolean isTrue(final String value) {
        return value != null && "true".equalsIgnoreCase(value.trim());
    }

    public static boolean isHex(final String hex) {
        final int len = hex.length();
        int i = 0;
        while (i < len) {
            final char ch = hex.charAt(i++);
            if ((ch < '0' || ch > '9') && (ch < 'A' || ch > 'F') && (ch < 'a' || ch > 'f')) {
                return false;
            }
        }
        return true;
    }

    static {
        URL_PATTERN = Pattern.compile("(http|https)\\://[A-Za-z0-9\\.\\-]+(/[A-Za-z0-9\\?\\&\\=;\\+!'\\(\\)\\*\\-\\._~%]*)*");
        EMAIL_PATTERN = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        NUMBER_PATTERN = Pattern.compile("^[-]?\\d+([\\.,]\\d+)?$");
        USERNAME_PATTERN = Pattern.compile("^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*$");
        IMAGE_EXT_PATTERN = Pattern.compile("([^\\s]+(\\.(?i)(jpg|png|gif|tiff|bmp|jpeg))$)");
        IMAGE_CONTENT_TYPE_PATTERN = Pattern.compile("^(?i)(image/jpeg|image/png|image/gif|image/tiff|image/bmp)$");
    }
}
