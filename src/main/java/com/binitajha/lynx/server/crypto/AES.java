package com.binitajha.lynx.server.crypto;

import com.binitajha.lynx.server.model.Secret;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;

import static java.util.Arrays.compare;

public class AES {

    private static BigInteger rootkey;

    static {
        Instant x = Instant.now();
        String y = String.format("%d%09d", x.getNano(), x.getEpochSecond());
        System.out.println(y);
        rootkey = new BigInteger(y);
    }

    public AES () throws NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("AES");
    }


    public Secret encrypt(Secret es, X509Certificate cert) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Key secretKey = getSecretKey(cert);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        System.out.println("Before enc: " + es.data + ":[" + es.encrypted + "]");

        byte[] encryptedBytes = cipher.doFinal(es.data.getBytes(StandardCharsets.UTF_8));

        es.encrypted = encode(encryptedBytes);
        if(compare(encryptedBytes, decode(es)) != 0) {
            System.out.println(Arrays.toString(encryptedBytes));
            System.out.println(Arrays.toString(decode(es)));

        };
        System.out.println("After enc: " + es.data + ":[" + es.encrypted + "]");
        decrypt(es, cert);
//        String key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
//        System.out.println(String.format("%s + %s = %s", es.data, key, es.encrypted));
        return es;
    }

    private static byte[] decode(Secret es) {
        String resp = es.encrypted;
        if(resp.endsWith("A")) {
            resp = resp.substring(0, resp.length() - 1) + "==";
        } else if(resp.endsWith("B")) {
            resp = resp.substring(0, resp.length() - 1) + "=";
        }
        System.out.println("Response: " + resp);
        return Base64.getDecoder().decode(resp);
    }

    private static String encode(byte[] encryptedBytes) {

        String resp = Base64.getEncoder().encodeToString(encryptedBytes);
        String resp2 = null;
        if(resp.endsWith("==")) {
            resp2 = resp.substring(0, resp.length() - 2) + "A";
        } else if(resp.endsWith("=")) {
            resp2 =resp.substring(0, resp.length() - 1) + "B";
        } else {
            resp2 = resp;
        }
        return resp2;
    }

    public Secret decrypt(Secret es, X509Certificate cert) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Key secretKey = getSecretKey(cert);

        Cipher cipher = Cipher.getInstance("AES");
        cipher. init(Cipher.DECRYPT_MODE, secretKey);
        byte[] crypt = decode(es);
        System.out.println(Arrays.toString(crypt));
        byte[] decryptedBytes = cipher.doFinal(crypt);
        System.out.println(Arrays.toString(decryptedBytes));
        es.data = new String(decryptedBytes, StandardCharsets.UTF_8);
        System.out.println(es.data + ":"  + es.encrypted);
        return es;

    }

    protected Key getSecretKey(X509Certificate cert) {

        byte[] keyBytes = getBytes(rootkey, cert.getSerialNumber());

        return new SecretKeySpec(keyBytes, "AES");
    }

    protected static byte[] getBytes(BigInteger keyInt, BigInteger keyInt2) {
        byte[] keyBytes = new byte[16];
        int k = 0;
        byte[] bytes = keyInt.toByteArray();

        for (byte b : bytes) {
            keyBytes[k] = b;
            k ++;
        }

        k = 8;
        int b = 0;
        bytes = keyInt2.toByteArray();
        while(k < 16) {
            keyBytes[k] = bytes[b];
            k ++; b++;
        }
        return keyBytes;
    }

}
