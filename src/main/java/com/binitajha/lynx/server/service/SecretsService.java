package com.binitajha.lynx.server.service;

import com.binitajha.lynx.server.crypto.AES;
import com.binitajha.lynx.server.model.Secret;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

    private final static Logger log = LoggerFactory.getLogger(SecretsService.class.getName());

    @Autowired
    private RedisTemplate<String, Secret> redisTemplate;

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

    public Secret store(X509Certificate cert, Secret secret) {
        try {
            redisTemplate.opsForValue().set(secret.getKey().trim(), secret);
            Secret s = retrieve(cert, secret);
            if(s == null || !s.getEncrypted().equals(secret.getEncrypted())) {
                log.error("Redis store failed " + secret.getEncrypted() + " : " + s);
                return null;
            } else {
                log.info(String.format("Key %s written to Redis for client %s", secret.getKey(), cert.getSubjectX500Principal()));
            }
        } catch (Exception ce) {
            log.error("Redis store failed",  ce);
            return null;
        }
        return secret;
    }

    public Secret retrieve(X509Certificate cert, Secret secret) {
        Secret resp = redisTemplate.opsForValue().get(secret.getKey().trim());
        log.info(secret.getKey() + ":" + (resp == null));
        return resp;
    }

}
