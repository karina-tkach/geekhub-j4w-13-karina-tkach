package org.geekhub.hw10.framework;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.util.Set;

@SuppressWarnings("all")
public class ClassLoaderFromPackage {
    public Set<Class<?>> findAllClassesUsingClassLoader(String packageName) {
        try (InputStream stream = ClassLoader.getSystemClassLoader()
            .getResourceAsStream(packageName.replaceAll("[.]", "/"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream))){
            return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getClass(line, packageName))
                .collect(Collectors.toSet());
        }
        catch(Exception e){
            throw new RuntimeException(e.getMessage(),e);
        }
    }
@SuppressWarnings("java:S108")
    private Class<?> getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException ignored) {
        }
        return null;
    }
}
