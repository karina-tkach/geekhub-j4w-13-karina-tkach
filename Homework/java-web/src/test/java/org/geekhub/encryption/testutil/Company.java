package org.geekhub.encryption.testutil;

import org.geekhub.encryption.injector.Injectable;

public class Company {
    @Injectable(propertyName = "name")
    private String name;
    @Injectable(propertyName = "worker")
    private Person worker;
}
