package org.geekhub.encryption;

import org.geekhub.encryption.consoleapi.Menu;
import org.geekhub.encryption.injector.InjectionExecutor;

public class ApplicationStarter {
    public static void main(String[] args) {
        InjectionExecutor injectionExecutor = new InjectionExecutor("Homework/java-web/src/main/resources/application.properties");
        //injectionExecutor.execute(cl);
    }
}
