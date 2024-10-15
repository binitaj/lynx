package com.binitajha.lynx.server.audit;

import com.binitajha.lynx.server.crypto.Hasher;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;

import java.security.NoSuchAlgorithmException;

public class AuditFormatter extends LayoutBase<ILoggingEvent> {


    private Hasher hasher;
    public AuditFormatter() throws NoSuchAlgorithmException {
        hasher = new Hasher();
    }

    @Override
    public String doLayout(ILoggingEvent event) {

        return String.format("%s: %s (at %s:%d)%n",
                event.getLevel(),
                event.getFormattedMessage(),
                event.getLoggerName(),
                event.getCallerData()[0].getLineNumber());
    }
}
