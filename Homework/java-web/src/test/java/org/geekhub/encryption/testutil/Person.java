package org.geekhub.encryption.testutil;

import org.geekhub.encryption.injector.Injectable;

public class Person {
    @Injectable(propertyName = "name")
    private String name;
    @Injectable(propertyName = "age")
    private int age;
    @Injectable(propertyName = "isStudent")
    private boolean isStudent;
    private final String isSmart;

    public Person() {
        isSmart = "true";
    }

    public String getInfo() {
        return String.format("%s %d %s %s", name, age, isStudent, isSmart);
    }
}
