package com.binitajha.lynx.server.service;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EncryptionFailedException extends Throwable {

    public Exception exception;
    public EncryptionFailedException(Exception e) {
        super(e);
        this.exception = e;
    }
}
