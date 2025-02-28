package com.demoproject.framework.util.crypto;

public class Base64
{
    public static String encode(final String input) {
        final byte[] encryptBytes = org.apache.commons.codec.binary.Base64.encodeBase64(input.getBytes());
        return new String(encryptBytes);
    }
    
    public static String decode(final String input) {
        final byte[] decodedBytes = org.apache.commons.codec.binary.Base64.decodeBase64(input);
        return new String(decodedBytes);
    }
    
    public static byte[] encode(final byte[] input) {
        final byte[] encryptBytes = org.apache.commons.codec.binary.Base64.encodeBase64(input);
        return encryptBytes;
    }
    
    public static byte[] decode(final byte[] input) {
        final byte[] decodedBytes = org.apache.commons.codec.binary.Base64.decodeBase64(input);
        return decodedBytes;
    }
    
    public static String encodeUrlSafe(final String input) {
        final org.apache.commons.codec.binary.Base64 decoder = new org.apache.commons.codec.binary.Base64(true);
        final byte[] decodedBytes = decoder.encode(input.getBytes());
        return new String(decodedBytes);
    }
    
    public static String decodeUrlSafe(final String input) {
        final org.apache.commons.codec.binary.Base64 decoder = new org.apache.commons.codec.binary.Base64(true);
        final byte[] decodedBytes = decoder.decode(input);
        return new String(decodedBytes);
    }
    
    public static String escape(String text, final EEscape escape) {
        if (escape == EEscape.REPLACE) {
            text = text.replace("/", "_");
            text = text.replace("+", ".");
            text = text.replace("=", "-");
        }
        else if (escape == EEscape.REVERT) {
            text = text.replace("_", "/");
            text = text.replace(".", "+");
            text = text.replace("-", "=");
        }
        return text;
    }
    
    public enum EEscape
    {
        REPLACE, 
        REVERT;
    }
}
