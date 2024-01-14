package org.geekhub.encryption.injector;

import org.geekhub.encryption.exception.FileException;
import org.geekhub.encryption.exception.PropertyFormatException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertiesFileReader {
    private PropertiesFileReader() {

    }

    public static Map<String, String> readFile(String pathToFile) {
        Path propertiesFilePath = Path.of(pathToFile);
        try (Stream<String> input = Files.lines(propertiesFilePath)) {

            return input
                .filter(PropertiesFileReader::isLineValid)
                .collect(Collectors.toMap(
                    lineData -> lineData.split("=")[0].trim(),
                    lineData -> lineData.split("=")[1].trim()
                ));

        } catch (IOException ex) {
            throw new FileException("Error occurred while processing properties file", ex);
        }
    }

    private static boolean isLineValid(String line) {
        String[] data = line.split("=");

        if (line.isBlank() || data.length != 2 || data[0].trim().isEmpty() || data[1].trim().isEmpty()) {
            throw new PropertyFormatException("Line must contain property name & value, exactly one '=' symbol, and not to be blank");
        }

        return true;
    }
}

