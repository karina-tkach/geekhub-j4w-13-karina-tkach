package org.geekhub.encryption.service;

import org.geekhub.encryption.ciphers.Cipher;
import org.geekhub.encryption.repository.EncryptionRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class EncryptionService {
    private final EncryptionRepository encryptionRepository;
    private Cipher cipher;

    public EncryptionService(EncryptionRepository encryptionRepository, Cipher cipher) {
        this.encryptionRepository = encryptionRepository;
        this.cipher = cipher;
    }

    public String encryptMessage(String message) {
        if (message.isBlank()) {
            throw new IllegalArgumentException("Incorrect data for encryption.");
        }

        String encryptedMessage = cipher.encrypt(message);

        OffsetDateTime dateTime = OffsetDateTime.now();
        encryptionRepository.addToHistoryLog(message, cipher.getCipherName(), encryptedMessage, dateTime);

        return encryptedMessage;
    }

    public void setCipher(Cipher cipher){
        this.cipher = cipher;
    }
}
