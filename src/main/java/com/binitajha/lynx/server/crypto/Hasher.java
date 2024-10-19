package com.binitajha.lynx.server.crypto;

import com.binitajha.lynx.server.controller.SecretsController;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Hasher {

    static final Logger log = LoggerFactory.getLogger(Hasher.class.getName());

    private SHA256Hasher hasher;
    private SimpleDateFormat formatter;

    public Hasher() throws NoSuchAlgorithmException {
        this.hasher = new SHA256Hasher();
        formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ssa");
    }

    public String hashedMessage(X509Certificate cert, String message) {

        String principal = cert.getSubjectX500Principal().getName();
        BigInteger certNumber = cert.getSerialNumber();
        String  msg = String.format("%s:%s:%s: %s ",
                dateString(), hasher.sha256(certNumber+principal+message), principal,   message);
        log.info("Debug:" + certNumber +  principal);
        return msg;

    }

    public String dateString() {
        return formatter.format(new Date());
    }

}
