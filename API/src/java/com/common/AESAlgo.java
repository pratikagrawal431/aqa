package com.common;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author narsimhareddy.s
 */
import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import org.apache.catalina.util.RequestUtil;
import sun.misc.*;

public class AESAlgo {

    private static final String ALGO = "AES";
    private static final byte[] keyValue =
            new byte[]{'T', 'h', 'e', 'B', 'e', 's', 't', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y'};

    public static void main(String[] args) {
        try {
            System.out.println("args = " + decrypt("WiE0i6CreXTAamucQlIVAQ=="));
            System.out.println("args = " + decrypt("UQxhknYjnMHrVQU7nV15JQ=="));
        } catch (Exception ex) {
            Logger.getLogger(AESAlgo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String encrypt(String Data) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = new BASE64Encoder().encode(encVal);
        return encryptedValue;
    }

    public static String decrypt(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }

    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec("test@54326123456".getBytes(), ALGO);
        return key;
    }
}
