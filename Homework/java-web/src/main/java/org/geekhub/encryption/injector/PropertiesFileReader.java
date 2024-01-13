package org.geekhub.encryption.injector;

import org.geekhub.encryption.exception.FileException;
import org.geekhub.encryption.exception.PropertyFormatException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.nio.file.Files;

import static java.nio.file.Files.readAllLines;

public class PropertiesFileReader {
    private PropertiesFileReader() {

    }

    public static Map<String, String> readFile(String pathToFile) {
        Path propertiesFilePath = Path.of(pathToFile);
        Map<String, String> properties = new HashMap<>();
        try {
            if (!Files.exists(propertiesFilePath)) {
                throw new FileNotFoundException("Property file was not found.");
            }
            List<String> linesFromFile = readAllLines(propertiesFilePath);
            for (String line : linesFromFile) {
                if (isLineValid(line)) {
                    String[] lineData = line.split("=");
                    String propertyName = lineData[0].trim();
                    String propertyValue = lineData[1].trim();
                    if (propertyName.isEmpty() || propertyValue.isEmpty()) {
                        throw new PropertyFormatException("Property name or value can`t be empty or contain just whitespaces.");
                    }
                    properties.put(propertyName, propertyValue);
                } else {
                    throw new PropertyFormatException("Line containing property name and value can't be empty and must contain exactly one '=' symbol");
                }
            }
        } catch (IOException ex) {
            throw new FileException("Error occurred while properties file", ex);
        }
        return properties;

    }

    private static boolean isLineValid(String line) {
        return !line.isBlank() && line.split("=").length == 2;
    }
}

