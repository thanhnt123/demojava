package com.demoproject.framework.util.crypto;

import org.apache.commons.codec.binary.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.GeneralSecurityException;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import org.apache.log4j.Logger;

public class Rijndael
{
    private static final Logger logger;
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String ALGORITHM = "AES";
    private static final String DIGEST = "MD5";
    private static Cipher _cipher;
    private static SecretKey _password;
    private static IvParameterSpec _IVParamSpec;
    
    public Rijndael(final String password) {
        try {
            final byte[] IV = password.getBytes();
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            Rijndael._password = new SecretKeySpec(digest.digest(IV), "AES");
            Rijndael._cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            Rijndael._IVParamSpec = new IvParameterSpec(IV);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException ex3) {
            final GeneralSecurityException ex2;
            final GeneralSecurityException ex = ex3;
            Rijndael.logger.error((Object)ex.toString());
        }
    }
    
    public String encrypt(final String text) {
        byte[] encryptedData;
        try {
            Rijndael._cipher.init(1, Rijndael._password, Rijndael._IVParamSpec);
            encryptedData = Rijndael._cipher.doFinal(text.getBytes());
        }
        catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex3) {
            final GeneralSecurityException ex2;
            final GeneralSecurityException ex = ex3;
            Rijndael.logger.error((Object)ex.toString());
            return "";
        }
        return new String(Base64.encodeBase64(encryptedData));
    }
    
    public String decrypt(final String text) {
        try {
            Rijndael._cipher.init(2, Rijndael._password, Rijndael._IVParamSpec);
            final byte[] decodedValue = Base64.decodeBase64(text.getBytes());
            final byte[] decryptedVal = Rijndael._cipher.doFinal(decodedValue);
            return new String(decryptedVal);
        }
        catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex3) {
            final GeneralSecurityException ex2;
            final GeneralSecurityException ex = ex3;
            Rijndael.logger.error((Object)ex.toString());
            return "";
        }
    }
    
    static {
        logger = Logger.getLogger((Class)Rijndael.class);
    }
}
