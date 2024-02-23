package org.geekhub.encryption.ciphers;

import org.geekhub.encryption.ciphers.CipherAlgorithm;
import org.geekhub.encryption.ciphers.OperationType;

public class EncodeDataDTO {
    private OperationType operationType;
    private String originalMessage;
    private CipherAlgorithm cipherAlgorithm;
    private int shift;
    private String key;

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public String getOriginalMessage() {
        return originalMessage;
    }

    public void setOriginalMessage(String originalMessage) {
        this.originalMessage = originalMessage;
    }

    public CipherAlgorithm getCipherAlgorithm() {
        return cipherAlgorithm;
    }

    public void setCipherAlgorithm(CipherAlgorithm cipherAlgorithm) {
        this.cipherAlgorithm = cipherAlgorithm;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
