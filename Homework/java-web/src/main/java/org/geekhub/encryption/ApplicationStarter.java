package org.geekhub.encryption;

import org.geekhub.encryption.models.HistoryEntry;
import org.geekhub.encryption.repository.EncryptionRepositoryInMemory;
import org.geekhub.encryption.service.EncryptionService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class ApplicationStarter {

    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.geekhub.encryption.configurations");
        context.registerShutdownHook();

        /*DataSource bean = context.getBean(DataSource.class);
        boolean isValid = bean.getConnection().isValid(5000);
        System.out.println(isValid);*/


        /*EncryptionService encryptionService = context.getBean(EncryptionService.class);
        String originalMessage = "Geekhub";
        String encryptedMessage = encryptionService.performOperation(originalMessage);
        System.out.printf("%s%n%n", encryptedMessage);*/
        OffsetDateTime from = OffsetDateTime.of(2024,2,3,20,0,0,0, ZoneOffset.ofHours(2));
        OffsetDateTime to = OffsetDateTime.of(2024,2,3,20,01,48,161196, ZoneOffset.ofHours(2));
        EncryptionRepositoryInMemory repo = context.getBean(EncryptionRepositoryInMemory.class);
        for(HistoryEntry entry : repo.getHistoryInDateRange(null,null)){
            System.out.println(entry);
        }


    }
}
