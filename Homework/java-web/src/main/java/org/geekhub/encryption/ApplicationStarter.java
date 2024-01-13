package org.geekhub.encryption;

import org.geekhub.encryption.consoleapi.CreateEncryption;
import org.geekhub.encryption.consoleapi.GetEncryption;
import org.geekhub.encryption.consoleapi.Menu;
import org.geekhub.encryption.injector.InjectionExecutor;
import org.geekhub.encryption.repository.EncryptionRepository;
import org.geekhub.encryption.service.EncryptionService;
import org.geekhub.encryption.service.LogService;

import java.util.Scanner;

public class ApplicationStarter {
    public static void main(String[] args) {
        String pathToPropertiesFile = ClassLoader.getSystemResource("application.properties").getPath().substring(1);
        InjectionExecutor injectionExecutor = new InjectionExecutor(pathToPropertiesFile);

        Scanner scanner = new Scanner(System.in);

        EncryptionRepository repository = new EncryptionRepository();
        EncryptionService encryptionService = new EncryptionService(repository);
        injectionExecutor.execute(encryptionService);
        LogService logService = new LogService(repository);
        CreateEncryption createEncryption = new CreateEncryption(scanner, encryptionService);
        GetEncryption getEncryption = new GetEncryption(scanner, logService);

        Menu menu = new Menu(scanner, createEncryption, getEncryption);
        menu.printMenu();
    }
}
