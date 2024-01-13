package org.geekhub.encryption;

import org.geekhub.encryption.exception.NoPropertyIsFoundException;
import org.geekhub.encryption.exception.UnapplyableFieldTypeException;
import org.geekhub.encryption.injector.InjectionExecutor;
import org.geekhub.encryption.testutil.Company;
import org.geekhub.encryption.testutil.Person;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InjectionExecutorTest {
    private static final Path PATH_TO_FILE = Path.of("application.properties");
    private static final String STRING_PATH = "application.properties";
    private static Person person;
    private static Company company;

    @BeforeAll
    static void setUp() throws IOException {
        person = new Person();
        company = new Company();
        Files.createFile(PATH_TO_FILE);
    }

    @AfterAll
    static void delete() throws IOException {
        Files.delete(PATH_TO_FILE);
    }

    @Test
    void shouldThrowException_whenUnapplyableFieldType() throws IOException {
        Files.write(PATH_TO_FILE, "name=SPD\nworker=Vlad".getBytes());
        assertThatCode(() -> new InjectionExecutor(STRING_PATH).execute(company))
            .isInstanceOf(UnapplyableFieldTypeException.class)
            .hasMessage("Cannot apply @Injectable to the field with type org.geekhub.encryption.testutil.Person");
    }

    @Test
    void shouldThrowException_whenNoPropertyIsFound() throws IOException {
        Files.write(PATH_TO_FILE, "name=Oleg\nage=18\n".getBytes());
        assertThatCode(() -> new InjectionExecutor(STRING_PATH).execute(person))
            .isInstanceOf(NoPropertyIsFoundException.class)
            .hasMessage("No property data for field isStudent was found");
    }

    @Test
    void shouldInjectValues_whenValidDataIsProvided() throws IOException {
        Files.write(PATH_TO_FILE, "name=Anna\nage=18\nisStudent=true".getBytes());
        new InjectionExecutor(STRING_PATH).execute(person);
        String actual = person.getInfo();
        String expected = "Anna 18 true true";

        assertEquals(expected, actual, "Field's should've been injected.");
    }
}
