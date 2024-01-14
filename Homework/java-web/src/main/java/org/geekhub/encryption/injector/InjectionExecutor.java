package org.geekhub.encryption.injector;

import org.geekhub.encryption.exception.FieldSettingException;
import org.geekhub.encryption.exception.NoPropertyIsFoundException;
import org.geekhub.encryption.exception.UnapplyableFieldTypeException;

import java.lang.reflect.Field;
import java.util.Map;

public class InjectionExecutor {
    private final Map<String, String> propertiesFromFile;

    public InjectionExecutor(String pathToPropertiesFile) {
        this.propertiesFromFile = PropertiesFileReader.readFile(pathToPropertiesFile);
    }

    public void execute(Object object) {
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Injectable.class)) {
                processAnnotatedField(field,object);
            }
        }
    }
    private void processAnnotatedField(Field field, Object object){
        String propertyName = field.getAnnotation(Injectable.class).propertyName();
        if (propertiesFromFile.containsKey(propertyName)) {
            setFieldValue(object, field, propertiesFromFile.get(propertyName));
        } else {
            throw new NoPropertyIsFoundException("No property data for field " + field.getName() + " was found");
        }
    }
    @SuppressWarnings("java:S3011")
    private void setFieldValue(Object instance, Field field, String value) {
        field.setAccessible(true);
        try {
            String typeName = field.getGenericType().getTypeName();
            switch (typeName) {
                case "boolean", "java.lang.Boolean" -> field.set(instance, Boolean.parseBoolean(value));
                case "int", "java.lang.Integer" -> field.set(instance, Integer.parseInt(value));
                case "byte", "java.lang.Byte" -> field.set(instance, Byte.parseByte(value));
                case "double", "java.lang.Double" -> field.set(instance, Double.parseDouble(value));
                case "float", "java.lang.Float" -> field.set(instance, Float.parseFloat(value));
                case "short", "java.lang.Short" -> field.set(instance, Short.parseShort(value));
                case "long", "java.lang.Long" -> field.set(instance, Long.parseLong(value));
                case "java.lang.String" -> field.set(instance, value);
                case "char" -> field.set(instance, value.charAt(0));
                default ->
                    throw new UnapplyableFieldTypeException("Cannot apply @Injectable to the field with type " + typeName);
            }

        } catch (IllegalAccessException ex) {
            throw new FieldSettingException("Error occurred while setting a field value", ex);
        }
    }
}

