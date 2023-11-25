package org.geekhub.hw6;

import org.geekhub.hw6.exception.CatFactException;
import org.geekhub.hw6.exception.FileException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatFactWriterTest {

    @Mock
    private CatFactService catFactService;

    private final int TIME_INTERVAL = 1000;
    private static final Path PATH_TO_FILE = Path.of(ClassLoader.getSystemResource("catFactTest.txt")
            .getPath().substring(1)); //Path.of("/Homework/src/test/resources/catFactTest.txt");
    private CatFactWriter catFactWriter;
    private static final Path INVALID_PATH = Path.of("/src/test1/resources/catFactTest.txt");

    @BeforeEach
    public void setUp() throws IOException {
        catFactWriter = new CatFactWriter(catFactService, TIME_INTERVAL, PATH_TO_FILE);
        Files.deleteIfExists(PATH_TO_FILE);
    }

    @AfterAll
    public static void deleteFile() throws IOException {
        Files.deleteIfExists(PATH_TO_FILE);
    }

    @Test
    void writeFactsToFile_successfulWrite() throws InterruptedException, IOException, CatFactException {
        when(catFactService.getRandomCatFact()).thenReturn("This is a cat fact");
        catFactWriter.writeFactsToFile();

        assertTrue(Files.exists(PATH_TO_FILE), "The file was not created.");

        List<String> lines = Files.readAllLines(PATH_TO_FILE);
        assertEquals(2, lines.size(), "The file was expected to have 2 lines only.");
        assertEquals("This is a cat fact", lines.get(0), "The cat fact was not written to the file.");
    }

    @Test
    void writeFactsToFile_whenUnsuccessfulResponse() throws InterruptedException, CatFactException, IOException {
        when(catFactService.getRandomCatFact())
                .thenThrow(new CatFactException("Failed to get cat fact", new IOException()));
        catFactWriter.writeFactsToFile();

        assertTrue(Files.exists(PATH_TO_FILE), "The file was not created.");

        List<String> lines = Files.readAllLines(PATH_TO_FILE);
        assertEquals(1, lines.size(), "The file was expected to have 1 line only.");
        assertEquals("I don't know", lines.get(0), "The cat fact was written to the file.");
    }

    @Test
    void writeFactsToFile_whenUnableToCreate() {
        catFactWriter = new CatFactWriter(catFactService, TIME_INTERVAL, INVALID_PATH);
        assertThatCode(() -> catFactWriter.writeFactsToFile())
                .isInstanceOf(FileException.class)
                .hasMessage(String.format("Fail to create file with provided path: %s", INVALID_PATH));
    }
}
