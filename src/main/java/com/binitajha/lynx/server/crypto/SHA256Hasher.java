package com.binitajha.lynx.server.crypto;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;



public class SHA256Hasher {
    private MessageDigest digest;
    public SHA256Hasher() throws NoSuchAlgorithmException {
        digest = MessageDigest.getInstance("SHA-256");
    }
    public String sha256(String input) {
        byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }
}

