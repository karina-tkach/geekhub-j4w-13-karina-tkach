package org.geekhub.encryption;

import org.geekhub.encryption.service.EncryptionService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;

public class ApplicationStarter {

    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.geekhub.encryption.configurations");
        context.registerShutdownHook();

        /*DataSource bean = context.getBean(DataSource.class);
        boolean isValid = bean.getConnection().isValid(5000);
        System.out.println(isValid);*/


        EncryptionService encryptionService = context.getBean(EncryptionService.class);
        String originalMessage = "Hello World!";
        String encryptedMessage = encryptionService.performOperation(originalMessage);
        System.out.printf("%s%n%n", encryptedMessage);


    }
}
