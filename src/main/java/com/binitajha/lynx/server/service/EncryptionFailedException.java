package com.binitajha.lynx.server.service;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.crypto.NoSuchPaddingException;

@Data
@EqualsAndHashCode(callSuper = false)
public class EncryptionFailedException extends Throwable {

    private Exception e;
    public EncryptionFailedException(Exception e) {
        super(e);
        this.e = e;
    }
}
