package org.geekhub.hw6;

import org.geekhub.hw6.exception.ArgumentsException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class MainTest {

    private static final Path filePath = Path.of("test.txt");

    @AfterAll
    public static void deleteFile() throws IOException {
        Files.deleteIfExists(filePath);
    }

    @Test
    void main_notEnoughArgumentsProvided() {
        String[] arguments = new String[]{"1"};
        assertThatCode(() -> Main.main(arguments)).isInstanceOf(ArgumentsException.class)
                .hasMessage("Both time interval and path should be provided via command-line arguments");
    }

    @Test
    void main_invalidFirstArgument() {
        String[] arguments = new String[]{"a", "test.txt"};
        assertThatCode(() -> Main.main(arguments)).isInstanceOf(ArgumentsException.class)
                .hasMessage("First argument must be numeric value");
    }

    @Test
    void main_invalidSecondArgument() {
        String[] arguments = new String[]{"100", ">"};
        assertThatCode(() -> Main.main(arguments)).isInstanceOf(ArgumentsException.class)
                .hasMessage("Second argument cannot be converted to a Path");
    }

    @Test
    void main_successfulExecution() {
        String[] arguments = new String[]{"100", filePath.toString()};
        assertThatCode(() -> Main.main(arguments)).doesNotThrowAnyException();
        assertTrue(Files.exists(filePath), "The file was not created.");
    }
}
