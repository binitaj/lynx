package com.binitajha.lynx.server.service;

import com.binitajha.lynx.server.crypto.AES;
import com.binitajha.lynx.server.model.Secret;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Service
public class SecretsService {

    private AES aes;

    public SecretsService() throws EncryptionFailedException  {
        try {
            this.aes = new AES();
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            throw new EncryptionFailedException (e);
        }
    }

    public Secret encrypt(X509Certificate cert, Secret secret) throws EncryptionFailedException {
        Secret toStore = new Secret(secret);
        try {
            aes.encrypt(toStore, cert);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException |
                 InvalidKeyException e) {
            throw new EncryptionFailedException(e);
        }
        return toStore;
    }

    public Secret decrypt(X509Certificate cert, Secret secret) throws EncryptionFailedException {
        Secret toStore = new Secret(secret);
        try {
            aes.decrypt(toStore, cert);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException |
                 InvalidKeyException e) {
            throw new EncryptionFailedException(e);
        }
        return toStore;
    }
}
