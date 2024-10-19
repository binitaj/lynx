package com.binitajha.lynx.server.crypto;

import com.binitajha.lynx.server.model.Secret;
import lombok.extern.slf4j.Slf4j;

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

@Slf4j
public class AES {

    private static BigInteger rootkey;

    static {
        Instant x = Instant.now();
        String y = String.format("%d%09d", x.getNano(), x.getEpochSecond());
        log.debug(y);
        rootkey = new BigInteger(y);
    }

    public AES () throws NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("AES");
    }


    public Secret encrypt(Secret es, X509Certificate cert) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Key secretKey = getSecretKey(cert);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        log.debug("Before enc: " + es.getData() + ":[" + es.getEncrypted() + "]");

        byte[] encryptedBytes = cipher.doFinal(es.getData().getBytes(StandardCharsets.UTF_8));
        es.setEncrypted(encode(encryptedBytes));
        decrypt(es, cert);
        return es;
    }

    public Secret decrypt(Secret es, X509Certificate cert) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Key secretKey = getSecretKey(cert);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] crypt = decode(es.getEncrypted());
        byte[] decryptedBytes = cipher.doFinal(crypt);
        es.setData(new String(decryptedBytes, StandardCharsets.UTF_8));
        return es;
    }

    private static byte[] decode(String encrypted) {
        String resp = encrypted;
        if(resp.endsWith("A")) {
            resp = resp.substring(0, resp.length() - 1) + "==";
        } else if(resp.endsWith("B")) {
            resp = resp.substring(0, resp.length() - 1) + "=";
        }
        byte[] respBytes = Base64.getDecoder().decode(resp);
        return padTo16(respBytes);
    }

    protected static byte[] padTo16(byte[] respBytes) {
        int len = respBytes.length;
        if(len % 16 != 0) {
            int words = (respBytes.length / 16);
            int pad = respBytes.length - words * 16;
            byte[] resp = new byte[(words + 1) * 16];
            System.arraycopy(respBytes, 0, resp, 0, respBytes.length);
            respBytes = resp;
        }
        return respBytes;

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
