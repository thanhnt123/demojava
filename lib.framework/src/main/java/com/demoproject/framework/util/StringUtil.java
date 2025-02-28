package com.demoproject.framework.util;

import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class StringUtil {

    private static final String DANGEROUS_CHARS = "&\"'<>";

    public static boolean containsDangerousCharacters(final String value) {
        for (int i = 0; i < value.length(); ++i) {
            if ("&\"'<>".indexOf(value.charAt(i)) >= 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDangerousCharacter(final char value) {
        return "&\"'<>".indexOf(value) >= 0;
    }

    public static String removeDangerousCharacter(String value) {
        if (isNullOrEmpty(value)) {
            value = "";
        }
        value = value.replaceAll("[&\"'<>]", "");
        return value;
    }

    public static boolean isNullOrEmpty(final String value) {
        return value == null || value.isEmpty();
    }

    public static String preview(final String value, final int length) {
        return preview(value, length, " ...");
    }

    public static String preview(final String value, final int length, final String tail) {
        if (value == null || value.length() <= length) {
            return value;
        }
        return value.substring(0, length - 1) + " " + tail;
    }

    public static int strlen(final String data) {
        return (data == null) ? 0 : data.length();
    }

    public static String toCamelCase(final String value) {
        if (isNullOrEmpty(value)) {
            return "";
        }
        final String[] parts = value.split("_");
        String camelCaseString = "";
        if (parts.length <= 1) {
            camelCaseString = toUpperCaseFirstChar(parts[0]);
        } else {
            for (final String part : parts) {
                camelCaseString += toProperCase(part);
            }
        }
        return camelCaseString;
    }

    public static String toProperCase(final String word) {
        if (isNullOrEmpty(word) || word.length() <= 1) {
            return word;
        }
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }

    public static String toLowerCase(final String value) {
        return (value != null) ? value.toLowerCase() : value;
    }

    public static String toLowerCaseFirstChar(final String value) {
        if (isNullOrEmpty(value)) {
            return value;
        }
        String output = value.substring(0, 1).toLowerCase();
        if (value.length() > 1) {
            output += value.substring(1);
        }
        return output;
    }

    public static String toUpperCase(final String value) {
        return (value != null) ? value.toUpperCase() : value;
    }

    public static String toUpperCaseFirstChar(final String value) {
        if (isNullOrEmpty(value)) {
            return value;
        }
        String output = value.substring(0, 1).toUpperCase();
        if (value.length() > 1) {
            output += value.substring(1);
        }
        return output;
    }

    public static boolean startsWithLowerCaseChar(final String data) {
        final int c0 = data.charAt(0);
        return c0 >= 97 && c0 <= 122;
    }

    public static String toToUpperCaseFirstCharOfWords(final String value) {
        final List<String> words = new ArrayList<String>();
        String output = "";
        final String[] delimiters = value.split("[\\s]");
        words.addAll(Arrays.asList(delimiters));
        for (int i = 0; i < words.size(); ++i) {
            output += toUpperCaseFirstChar(words.get(i));
            if (i < words.size() - 1) {
                output += " ";
            }
        }
        return output;
    }

    public static String replace(final String value, final String oldSymbol) {
        return replace(value, oldSymbol, "");
    }

    public static String replace(final String value, final String oldSymbol, final String newSymbol) {
        if (value == null || value.indexOf(oldSymbol) == -1) {
            return value;
        }
        final int oldLength = oldSymbol.length();
        final StringBuilder sb = new StringBuilder("");
        int i;
        int lastIndex;
        for (i = value.indexOf(oldSymbol, 0), lastIndex = 0; i != -1; i = value.indexOf(oldSymbol, lastIndex)) {
            sb.append(value.substring(lastIndex, i)).append(newSymbol);
            lastIndex = i + oldLength;
        }
        sb.append(value.substring(lastIndex));
        return sb.toString();
    }

    public static String stripSlashes(final String value) {
        if (value == null || value.indexOf(47) == -1) {
            return value;
        }
        return replace(value, "/", "");
    }

    public static Map<String, String> explode(final String value, final String separator) {
        return ConvertUtil.toMap(value, separator);
    }

    public static String implode(final String[] value, final String glue) {
        if (value == null || value.length == 0) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        for (final String item : value) {
            sb.append(item).append(glue);
        }
        String result = sb.toString();
        if (result.endsWith(glue)) {
            result = result.substring(0, result.lastIndexOf(glue));
        }
        return result;
    }

    public static String implode(final List<String> value, final String glue) {
        if (value == null || value.isEmpty()) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        for (final String item : value) {
            sb.append(item).append(glue);
        }
        String result = sb.toString();
        if (result.endsWith(glue)) {
            result = result.substring(0, result.lastIndexOf(glue));
        }
        return result;
    }

    public static String implode(final Map<String, ?> value, final String glue) {
        if (value == null || value.isEmpty()) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        for (final Map.Entry<String, ?> entry : value.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append(glue);
        }
        String result = sb.toString();
        if (result.endsWith(glue)) {
            result = result.substring(0, result.lastIndexOf(glue));
        }
        return result;
    }

    public static List<String> split(String bigValue, final String splitter) {
        if (isNullOrEmpty(bigValue)) {
            return new ArrayList<String>();
        }
        final List<String> dataList = new ArrayList<String>();
        final int spliterLen = splitter.length();
        for (int index = bigValue.indexOf(splitter); index != -1; index = bigValue.indexOf(splitter)) {
            final String frontString = bigValue.substring(0, index);
            dataList.add(frontString);
            bigValue = bigValue.substring(index + spliterLen);
        }
        if (!"".equals(bigValue)) {
            dataList.add(bigValue);
        }
        if (dataList.isEmpty()) {
            dataList.add(bigValue);
        }
        return dataList;
    }

    public static String removeUnicode(final String value) {
        if (value != null && value.length() > 0) {
            return value.replaceAll("[^\\p{ASCII}]", "");
        }
        return "";
    }

    public static String killUnicode(String value) {
        if (value != null && value.length() > 0) {
            value = value.replaceAll("\u0110", "D");
            value = value.replaceAll("\u0111", "d");
            return Normalizer.normalize(value, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        }
        return "";
    }

    public static boolean isUnicode(final String value) {
        return value != null && value.length() != removeUnicode(value).length();
    }

    public static String normalize(final String value) {
        if (value != null && value.length() > 0) {
            return Normalizer.normalize(value, Normalizer.Form.NFKC);
        }
        return "";
    }

    public static String slug(String value) {
        value = killUnicode(value).trim();
        if (value.length() > 0) {
            value = value.replaceAll("[^a-zA-Z0-9- ]*", "");
            if (value.contains(" ")) {
                value = value.replaceAll("[ ]{1,}", "-");
            }
        }
        return value;
    }

    public static String slug(final String value, final int wordCount) {
        final String slugString = cutWord(value, wordCount, false);
        return slug(slugString);
    }

    public static String digitFormat(final long value) {
        final NumberFormat formatter = new DecimalFormat("#,##0");
        return formatter.format(value);
    }

    public static String cutWord(final String value, final int wordCount, final boolean isPreview) {
        return cutWord(value, wordCount, 0, isPreview);
    }

    public static String cutWord(final String value, int wordCount, final int maxChars, final boolean isPreview) {
        final List<String> words = new ArrayList<String>();
        String output = "";
        final String[] delimiters = value.split("[\\s]+");
        words.addAll(Arrays.asList(delimiters));
        if (wordCount > words.size()) {
            wordCount = words.size();
        }
        for (int i = 0; i < wordCount; ++i) {
            String w = words.get(i);
            if (maxChars > 0 && w.length() > maxChars) {
                String wc = "";
                for (final String part : getParts(w, maxChars)) {
                    wc = wc + part + "... ";
                }
                w = wc;
            }
            output += w;
            if (i < wordCount - 1) {
                output += " ";
            }
        }
        if (isPreview) {
            output += " ...";
        }
        return output;
    }

    public static String cutChar(String value, final int charCount, final boolean isPreview) {
        if (charCount < value.length()) {
            value = value.substring(0, charCount);
        }
        if (isPreview) {
            value += " ...";
        }
        return value;
    }

    private static List<String> getParts(final String value, final int partitionSize) {
        final List<String> parts = new ArrayList<String>();
        for (int len = value.length(), i = 0; i < len; i += partitionSize) {
            parts.add(value.substring(i, Math.min(len, i + partitionSize)));
        }
        return parts;
    }
}
