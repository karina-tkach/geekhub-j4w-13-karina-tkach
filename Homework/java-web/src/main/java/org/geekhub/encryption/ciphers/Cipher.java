package org.geekhub.encryption.ciphers;

public interface Cipher {
    String encrypt(String message);
    String getCipherName();
}
