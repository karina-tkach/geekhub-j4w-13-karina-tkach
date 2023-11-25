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

    private final int timeInterval = 1000;
    private static final Path filePath = Path.of("test.txt");
    private CatFactWriter catFactWriter;

    @BeforeEach
    public void setUp() throws  IOException{
        catFactWriter = new CatFactWriter(catFactService, timeInterval, filePath);
        Files.deleteIfExists(filePath);
    }
    @AfterAll
    public static void deleteFile() throws IOException {
        Files.deleteIfExists(filePath);
    }
    @Test
    void writeFactsToFile_successfulWrite() throws InterruptedException, IOException, CatFactException {
        when(catFactService.getRandomCatFact()).thenReturn("This is a cat fact");
        catFactWriter.writeFactsToFile();

        assertTrue(Files.exists(filePath), "The file was not created.");

        List<String> lines = Files.readAllLines(filePath);
        assertEquals(2, lines.size(), "The file was expected to have 2 lines only.");
        assertEquals("This is a cat fact", lines.get(0), "The cat fact was not written to the file.");
    }

    @Test
    void writeFactsToFile_whenUnsuccessfulResponse() throws InterruptedException, CatFactException, IOException {
        when(catFactService.getRandomCatFact()).thenThrow(new CatFactException("Failed to get cat fact", new IOException()));
        catFactWriter.writeFactsToFile();

        assertTrue(Files.exists(filePath), "The file was not created.");

        List<String> lines = Files.readAllLines(filePath);
        assertEquals(1, lines.size(), "The file was expected to have 1 line only.");
        assertEquals("I don't know", lines.get(0), "The cat fact was written to the file.");
    }

    @Test
    void writeFactsToFile_whenUnableToCreate() {
        catFactWriter = new CatFactWriter(catFactService, timeInterval, Path.of("Homework\\java-core\\07-Unit-Test\\src\\main\\resources\\catFacts.txt"));
        assertThatCode(catFactWriter::writeFactsToFile)
            .isInstanceOf(FileException.class)
            .hasMessage("Fail to create file with provided path: Homework\\java-core\\07-Unit-Test\\src\\main\\resources\\catFacts.txt");
    }
}
