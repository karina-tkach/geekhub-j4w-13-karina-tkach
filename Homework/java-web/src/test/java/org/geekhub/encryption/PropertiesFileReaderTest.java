package org.geekhub.encryption;

import org.geekhub.encryption.exception.PropertyFormatException;
import org.geekhub.encryption.injector.PropertiesFileReader;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

class PropertiesFileReaderTest {
    private static final Path PATH_TO_FILE = Path.of("application.properties");
    private static final String STRING_PATH = "application.properties";

    @BeforeAll
    static void setUp() throws IOException {
        Files.createFile(PATH_TO_FILE);
    }

    @AfterAll
    static void delete() throws IOException {
        Files.delete(PATH_TO_FILE);
    }

    @Test
    void shouldThrowException_whenFileContainsEmptyLine() throws IOException {
        Files.write(PATH_TO_FILE, "\n".getBytes());
        assertThatCode(() -> PropertiesFileReader.readFile(STRING_PATH))
            .isInstanceOf(PropertyFormatException.class)
            .hasMessage("Line must contain property name & value, and exactly one '=' symbol");
    }

    @Test
    void shouldThrowException_whenNameIsBlank() throws IOException {
        Files.write(PATH_TO_FILE, "age=18\n =Anna".getBytes());
        assertThatCode(() -> PropertiesFileReader.readFile(STRING_PATH))
            .isInstanceOf(PropertyFormatException.class)
            .hasMessage("Property name or value can`t contain just whitespaces.");
    }

    @Test
    void shouldThrowException_whenValueIsBlank() throws IOException {
        Files.write(PATH_TO_FILE, "age=18\nname= ".getBytes());
        assertThatCode(() -> PropertiesFileReader.readFile(STRING_PATH))
            .isInstanceOf(PropertyFormatException.class)
            .hasMessage("Property name or value can`t contain just whitespaces.");
    }

    @Test
    void shouldThrowException_whenFileContainsInvalidSeparator() throws IOException {
        Files.write(PATH_TO_FILE, "name:Anna".getBytes());
        assertThatCode(() -> PropertiesFileReader.readFile(STRING_PATH))
            .isInstanceOf(PropertyFormatException.class)
            .hasMessage("Line must contain property name & value, and exactly one '=' symbol");
    }

    @Test
    void shouldReturnMap_whenValidFile() throws IOException {
        Files.write(PATH_TO_FILE, "name=Anna\nage=18\nisStudent=true".getBytes());
        Map<String, String> actual = PropertiesFileReader.readFile(STRING_PATH);
        Map<String, String> expected = new HashMap<>();
        expected.put("name", "Anna");
        expected.put("age", "18");
        expected.put("isStudent", "true");

        assertEquals(expected, actual, "Two maps should've been equal.");
    }
}
