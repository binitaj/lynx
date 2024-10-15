package com.binitajha.lynx.server.controller;

import com.binitajha.lynx.server.crypto.Hasher;
import com.binitajha.lynx.server.model.Secret;
import com.binitajha.lynx.server.service.EncryptionFailedException;
import com.binitajha.lynx.server.service.SecretsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@RestController
@Slf4j
public class SecretsController {

    private final Hasher hasher;

    @Autowired
    private SecretsService service;

    public SecretsController() throws NoSuchAlgorithmException {
        this.hasher = new Hasher();
    }

    @GetMapping("/hello")
    public String hello(HttpServletRequest request) {
        X509Certificate[] certs = (X509Certificate[]) request.getAttribute("jakarta.servlet.request.X509Certificate");
        String msg = hasher.hashedMessage(certs[0], "Received a ping");
        log.warn(msg);
        msg = String.format("Hello from Lynx server at %s. Client identified as %s", hasher.dateString(), certs[0].getSubjectX500Principal().getName());
        log.info("Returning: " + msg);
        return msg;
    }


    @PostMapping("/encrypt")
    public ResponseEntity<Secret> encrypt(@RequestBody Secret secret, HttpServletRequest request) {
        X509Certificate[] certs = (X509Certificate[]) request.getAttribute("jakarta.servlet.request.X509Certificate");
        X509Certificate cert = certs[0];
        String msg = hasher.hashedMessage(cert, String.format("Received encryption request %s", secret.id));
        log.warn(msg);
        try {
            Secret encrypted = service.encrypt(cert, secret);
            return ResponseEntity.ok(encrypted);
        } catch (EncryptionFailedException e) {
            e.getE().printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/decrypt")
    public ResponseEntity<Secret> decrypt(@RequestBody Secret secret, HttpServletRequest request) {
        X509Certificate[] certs = (X509Certificate[]) request.getAttribute("jakarta.servlet.request.X509Certificate");
        X509Certificate cert = certs[0];
        String msg = hasher.hashedMessage(cert, String.format("Received encryption request %s, key: %s", secret.id, secret.key));
        log.warn(msg);
        try {
            Secret encrypted = service.decrypt(cert, secret);
            return ResponseEntity.ok(encrypted);
        } catch (EncryptionFailedException e) {
            e.getE().printStackTrace();
            return ResponseEntity.badRequest().header("Error", e.getE().getMessage()).build();
        }
    }

    @PostMapping("/retrieve")
    public ResponseEntity<Secret> decrypt(@RequestBody Secret secret, HttpServletRequest request) {
        X509Certificate[] certs = (X509Certificate[]) request.getAttribute("jakarta.servlet.request.X509Certificate");
        X509Certificate cert = certs[0];
        String msg = hasher.hashedMessage(cert, String.format("Received retrieval request %s, key: %s", secret.id, secret.key));
        log.warn(msg);
        try {
            Secret encrypted = service.decrypt(cert, secret);
            return ResponseEntity.ok(encrypted);
        } catch (EncryptionFailedException e) {
            e.getE().printStackTrace();
            return ResponseEntity.badRequest().header("Error", e.getE().getMessage()).build();
        }
    }

}
