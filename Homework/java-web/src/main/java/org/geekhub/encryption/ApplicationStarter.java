package org.geekhub.encryption;

import org.geekhub.encryption.models.HistoryEntry;
import org.geekhub.encryption.repository.EncryptionRepositoryInMemory;
import org.geekhub.encryption.service.EncryptionService;
import org.geekhub.encryption.service.LogService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class ApplicationStarter {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.geekhub.encryption.configurations");
        context.registerShutdownHook();


        EncryptionService encryptionService = context.getBean(EncryptionService.class);
        String originalMessage = "Hello";
        String encryptedMessage = encryptionService.performOperation(originalMessage);
        System.out.printf("%s%n", encryptedMessage);
        /*OffsetDateTime from = OffsetDateTime.of(2024,2,3,20,0,0,0, ZoneOffset.ofHours(2));
        OffsetDateTime to = OffsetDateTime.of(2024,2,3,20,01,48,161196, ZoneOffset.ofHours(2));
        LogService logService = context.getBean(LogService.class);
        for(HistoryEntry entry : logService.getHistoryInDateRange(from,null)){
            System.out.println(entry);
        }*/


    }
}
