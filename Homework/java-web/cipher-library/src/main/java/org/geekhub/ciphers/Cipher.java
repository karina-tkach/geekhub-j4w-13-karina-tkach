package org.geekhub.ciphers;

public interface Cipher {
    String encrypt(String message);

    String decrypt(String message);

    String getCipherName();
}
