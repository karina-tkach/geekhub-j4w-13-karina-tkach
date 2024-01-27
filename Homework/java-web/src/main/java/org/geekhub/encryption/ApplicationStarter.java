package org.geekhub.encryption;

import org.geekhub.encryption.service.EncryptionService;
import org.geekhub.encryption.service.LogService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;


public class ApplicationStarter {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        context.registerShutdownHook();

        EncryptionService encryptionService = context.getBean(EncryptionService.class);
        String originalMessage = "Hello";
        String encryptedMessage = encryptionService.encryptMessage(originalMessage);
        System.out.printf("%s%n%n", encryptedMessage);

        LogService logService = context.getBean(LogService.class);
        List<String> fullHistory = logService.getFullHistory();
        printInfo("Full history:", fullHistory);

        String date = "2024-01-27";
        List<String> historyByDate = logService.getHistoryByDate(date);
        printInfo("History by date "+ date,historyByDate);

        List<String> algorithmUsageCount = logService.getAlgorithmUsageCount();
        printInfo("Algorithm usage count:",algorithmUsageCount);

        String cipherName = "Caesar";
        String uniqueEncryption = logService.getUniqueEncryptions(originalMessage,cipherName);
        System.out.printf("Number of encryptions of message '%s' by %s cipher%n",originalMessage,cipherName);
        System.out.println(uniqueEncryption);
    }

    private static void printInfo(String infoMessage, List<String> entries){
        System.out.println(infoMessage);
        for (String entry : entries){
            System.out.println(entry);
        }
        System.out.println("\n\n");
    }
}
