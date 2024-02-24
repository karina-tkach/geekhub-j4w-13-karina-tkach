package org.geekhub.ciphers;

class CipherUtil {
    private CipherUtil() {}
    static int getKeyLetter(int letter) {
        if (letter >= 'a' && letter <= 'z') {
            return 'a';
        }

        if (letter >= 'A' && letter <= 'Z') {
            return 'A';
        }

        return 0;
    }
}
