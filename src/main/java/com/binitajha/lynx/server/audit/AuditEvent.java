package com.binitajha.lynx.server.audit;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;

import java.security.cert.X509Certificate;

public class AuditEvent extends LoggingEvent {
    private X509Certificate certificate;

    public AuditEvent(String fqcn, LoggerContext loggerContext, Level level, String message, Throwable throwable, Object[] argArray) {
        super(fqcn, loggerContext.getLogger(fqcn), level, message, throwable, argArray);
    }

    public X509Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(X509Certificate certificate) {
        this.certificate = certificate;
    }
}
