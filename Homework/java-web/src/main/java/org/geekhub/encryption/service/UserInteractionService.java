package org.geekhub.encryption.service;

import org.geekhub.encryption.exception.UserException;
import org.geekhub.encryption.models.CipherAlgorithm;
import org.geekhub.encryption.models.EncodeDataDTO;
import org.geekhub.encryption.models.HistoryEntry;
import org.geekhub.encryption.models.OperationType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@Component
public class UserInteractionService {
    private final int activeUserId;
    private final EncodeDataDTO encodeDataDTO;
    private final EncryptionService encryptionService;
    private final HistoryService historyService;
    private final UserService userService;

    public UserInteractionService(@Value("${active.user.id}") int activeUserId,
                                  @Value("${user.originalMessage}") String originalMessage,
                                  @Value("${active.cipher.name}") String cipher,
                                  @Value("${operation.type}") String operationType,
                                  @Value("${caesar.key}") int caesarKey,
                                  @Value("${vigenere.key}") String vigenereKey,
                                  EncryptionService encryptionService,
                                  HistoryService historyService,
                                  UserService userService) {
        this.encodeDataDTO = new EncodeDataDTO();
        encodeDataDTO.setOriginalMessage(originalMessage);
        encodeDataDTO.setCipherAlgorithm(CipherAlgorithm.valueOf(cipher));
        encodeDataDTO.setOperationType(OperationType.valueOf(operationType));
        encodeDataDTO.setShift(caesarKey);
        encodeDataDTO.setKey(vigenereKey);

        this.activeUserId = activeUserId;
        this.encryptionService = encryptionService;
        this.historyService = historyService;
        this.userService = userService;
    }

    public void interactWithApplication() {
        if (!userService.isUserExist(activeUserId)) {
            throw new UserException("User with id " + activeUserId + " not exists");
        }

        String encryptedMessage = encryptionService.performOperation(encodeDataDTO);
        System.out.printf("%s%n", encryptedMessage);

        printFetchName("History by algorithm:");
        printHistory(historyService.getHistoryByAlgorithm("Caesar"));
        printDelimiters();

        printFetchName("History in date range:");
        printHistory(historyService.getHistoryInDateRange(null, OffsetDateTime.now()));
        printDelimiters();

        printFetchName("History bt algorithm and operation type:");
        printHistory(historyService.getHistoryByAlgorithmAndOperationType("Vigenere", "ENCRYPTION"));
        printDelimiters();

        printFetchName("History with pagination:");
        printHistory(historyService.getFullHistoryWithPagination(1, 2));
        printDelimiters();

        printFetchName("History with pagination and user id:");
        printHistory(historyService.getFullHistoryWithPaginationAndUserId(1, 1, 2));
        printDelimiters();
    }

    private static void printHistory(List<HistoryEntry> historyEntryList) {
        for (HistoryEntry entry : historyEntryList) {
            System.out.println(entry.stringRepresentation());
        }
    }

    private static void printDelimiters() {
        System.out.println("\n\n\n");
    }

    private static void printFetchName(String fetchName) {
        System.out.println(fetchName);
    }
}
