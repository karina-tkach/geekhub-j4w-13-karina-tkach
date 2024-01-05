package org.geekhub.hw10.framework;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;

public class CustomAssertions {
    private CustomAssertions() {
    }

    public static void assertEquals(Object expected, Object actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError("Expected: " + expected + " but got: " + actual);
        }
    }

    public static void assertThrows(Runnable runnable, Class<? extends Exception> type) {
        try {
            runnable.run();
            throw new AssertionError("Expected exception of type " + type.getName() + " was not thrown.");
        } catch (Exception exception) {
            if (!type.isInstance(exception)) {
                throw new AssertionError("Expected exception of type " + type.getName() + ", but got " + exception.getClass().getName());
            }
        }
    }

    @SuppressWarnings("java:S3011")
    public static void assertReflectionEquals(Object expected, Object actual) throws IllegalAccessException {
        Class<?> expectedClass = expected.getClass();
        Class<?> actualClass = expected.getClass();
        if (!expectedClass.equals(actualClass)) {
            throw new AssertionError("Object were of different class types");
        }
        Field[] expFields = Arrays.stream(expectedClass.getDeclaredFields())
            .sorted(Comparator.comparing(Field::getName)).toArray(Field[]::new);
        Field[] actFields = Arrays.stream(actualClass.getDeclaredFields())
            .sorted(Comparator.comparing(Field::getName)).toArray(Field[]::new);
        for (int i = 0; i < expFields.length; i++) {
            expFields[i].setAccessible(true);
            var exp = expFields[i].get(expected);
            actFields[i].setAccessible(true);
            var act = actFields[i].get(actual);
            if (!exp.equals(act)) {
                throw new AssertionError("Fields of objects were different. Expected: " + exp + " but got: " + act);
            }
        }
    }
}
