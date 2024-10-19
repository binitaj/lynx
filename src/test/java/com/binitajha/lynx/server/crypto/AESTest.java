package com.binitajha.lynx.server.crypto;

import com.binitajha.lynx.server.model.Secret;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigInteger;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Random;

import static com.binitajha.lynx.server.crypto.SecureRandomStringGenerator.generateSecureRandomString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

@Slf4j
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AESTest {

    static byte[] bytes = new byte[] { 0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38 };
    static BigInteger bi = new BigInteger(bytes);

    private static AES target;
    @MockBean private X509Certificate cert;

    private final Random random = new Random();

    @BeforeAll
    public static void init() throws Exception {
        target = new AES();
    }

    @BeforeEach
    public void begin() {
        lenient().
                when(cert.getSerialNumber()).
                thenReturn((bi));
    }

    byte[][] testBytes = {
            {0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    short[] testLengths = {16, 16, 32};

    @Test
    public void testPadding() {
        for(int i = 0; i < testBytes.length; ++i) {
            byte[] ret = AES.padTo16(testBytes[i]);
            assertEquals(testLengths[i], ret.length);
        }

    }

    /*
     *  Monte Carlo testing :)
     */
    @ExtendWith(MockitoExtension.class)
    @Test
    public void encrypt() throws Exception{
        Secret secret = new Secret("1", "100", "", "");

        for(int i = 0; i <50; ++i) {
            secret.setId(String.valueOf(i));
            int size = random.nextInt(500);
            secret.setData(generateSecureRandomString(size));
            Secret crypt = null;
            try {
                crypt = target.encrypt(secret, cert);
                Secret decrypt = target.decrypt(crypt, cert);
                log.debug(size + ",");
                System.out.flush();
                assertEquals(secret.getData(), decrypt.getData());
            } catch (Exception e) {
                System.err.println(secret + ": " + crypt);
                fail(secret + ": " + crypt, e);
            }
        }
    }

    @org.junit.jupiter.api.Test
    void decrypt() throws Exception {
        Secret secret = new Secret("1", "key", "simple text", "");
        target.encrypt(secret, cert);
    }

    @org.junit.jupiter.api.Test
    void getBytes() {
        String key = Base64.getEncoder().encodeToString(bytes);
        assertEquals("MDEyMzQ1Njc4OTAxMjM0NTY3OA==", key);

    }


}