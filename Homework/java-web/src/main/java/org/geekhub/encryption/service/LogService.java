package org.geekhub.encryption.service;

import org.geekhub.encryption.repository.EncryptionRepository;

import java.util.List;

public class LogService {
    private final EncryptionRepository encryptionRepository;
    public LogService(EncryptionRepository encryptionRepository){
        this.encryptionRepository = encryptionRepository;
    }
    public List<String> getHistory(){
        return encryptionRepository.getHistoryLog();
    }
}
