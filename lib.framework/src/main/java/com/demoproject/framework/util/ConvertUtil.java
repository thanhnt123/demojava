package com.demoproject.framework.util;

import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class ConvertUtil {

    private static final Logger logger;

    public static int toInt(final Object value, final int defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        try {
            return Integer.valueOf(value.toString());
        } catch (NumberFormatException ex) {
            ConvertUtil.logger.info((Object) ex.getMessage());
            return defaultValue;
        }
    }

    public static int toInt(final Object value) {
        return toInt(value, 0);
    }

    public static double toDouble(final Object value, final double defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        try {
            return Double.valueOf(value.toString());
        } catch (NumberFormatException ex) {
            ConvertUtil.logger.info((Object) ex.getMessage());
            return defaultValue;
        }
    }

    public static double toDouble(final Object value) {
        return toDouble(value, 0.0);
    }

    public static float toFloat(final Object value, final float defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Number) {
            return ((Number) value).floatValue();
        }
        try {
            return Float.valueOf(value.toString());
        } catch (NumberFormatException ex) {
            ConvertUtil.logger.info((Object) ex.getMessage());
            return defaultValue;
        }
    }

    public static float toFloat(final Object value) {
        return toFloat(value, 0.0f);
    }

    public static long toLong(final Object value, final long defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        try {
            return Long.valueOf(value.toString());
        } catch (NumberFormatException ex) {
            ConvertUtil.logger.info((Object) ex.getMessage());
            return defaultValue;
        }
    }

    public static long toLong(final Object value) {
        return toLong(value, 0L);
    }

    public static short toShort(final String value, final short defaultValue) {
        if (ValidUtil.isNumber(value)) {
            try {
                return Short.parseShort(value);
            } catch (NumberFormatException ex) {
                ConvertUtil.logger.info((Object) "toShort error", (Throwable) ex);
            }
        }
        return defaultValue;
    }

    public static short toShort(final String value) {
        return toShort(value, Short.parseShort("0"));
    }

    public static short toShort(final Object value) {
        if (value != null) {
            return toShort(value.toString(), Short.parseShort("0"));
        }
        return 0;
    }

    public static byte toByte(final String value) {
        return toByte(value, Byte.parseByte("0"));
    }

    public static byte toByte(final String value, final byte defaultValue) {
        if (ValidUtil.isNumber(value)) {
            try {
                return Byte.parseByte(value);
            } catch (NumberFormatException ex) {
                ConvertUtil.logger.info((Object) ex.getMessage());
            }
        }
        return defaultValue;
    }

    public static String toString(final Object input) {
        return (input != null) ? input.toString() : "";
    }

    public static String toString(final Object input, final String defaultValue) {
        return (input != null) ? input.toString() : defaultValue;
    }

    public static boolean toBoolean(final String input) {
        return toBoolean(input, false);
    }

    public static boolean toBoolean(final String input, final boolean defaultValue) {
        if (input == null) {
            return defaultValue;
        }
        boolean value = defaultValue;
        if ("TRUE".equalsIgnoreCase(input.toString()) || "T".equalsIgnoreCase(input.toString()) || "YES".equalsIgnoreCase(input.toString()) || "Y".equalsIgnoreCase(input.toString()) || "ON".equalsIgnoreCase(input.toString()) || "1".equalsIgnoreCase(input.toString())) {
            value = true;
        }
        return value;
    }

    public static Map<String, String> toMap(final String nameValuePairs, final String propertyDelimiter) {
        return toMap(nameValuePairs, "=", propertyDelimiter);
    }

    public static Map<String, String> toMap(final String nameValuePairs, final String nameValueSpliter, final String propertyDelimiter) {
        if (nameValuePairs == null || "".equals(nameValuePairs.trim())) {
            return new HashMap<String, String>();
        }
        final int spliterLength = nameValueSpliter.length();
        final StringTokenizer st = new StringTokenizer(nameValuePairs, propertyDelimiter);
        final Map<String, String> dataMap = new HashMap<String, String>();
        while (st.hasMoreTokens()) {
            final String tmp = st.nextToken().trim();
            final int equalIndex = tmp.indexOf(nameValueSpliter);
            if (equalIndex == -1) {
                continue;
            }
            final String name = tmp.substring(0, equalIndex).trim();
            final String value = tmp.substring(equalIndex + spliterLength).trim();
            dataMap.put(name, value);
        }
        return dataMap;
    }

    public static byte[] toByteArray(final String value) {
        return value.getBytes();
    }

    public static String toString(final byte[] value) {
        return new String(value);
    }

    static {
        logger = Logger.getLogger((Class) ConvertUtil.class);
    }
}
