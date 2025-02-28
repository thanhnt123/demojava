package com.demoproject.framework.util;

import com.demoproject.framework.util.crypto.Hex;
import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.nio.charset.Charset;

public class EncryptUtil
{
    public static byte[] encodeUTF8(final String text) {
        if (text == null) {
            return new byte[0];
        }
        return text.getBytes(Charset.forName("UTF-8"));
    }
    
    public static String decodeUTF8(final byte[] data) {
        if (data == null) {
            return "";
        }
        return new String(data, Charset.forName("UTF-8"));
    }
    
    public static String toMD5(final String text) {
        if (text == null) {
            return "";
        }
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            final byte[] resultByte = messageDigest.digest(text.getBytes());
            return Hex.hexFromByte(resultByte);
        }
        catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static String urlEncode(final String text) {
        if (text == null) {
            return "";
        }
        try {
            return URLEncoder.encode(text, "UTF-8");
        }
        catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static String urlDecode(final String text) {
        if (text == null) {
            return "";
        }
        try {
            return URLDecoder.decode(text, "UTF-8");
        }
        catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }
}
