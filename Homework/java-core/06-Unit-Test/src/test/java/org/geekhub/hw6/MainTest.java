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

    private static final Path PATH_OF_FILE = Path.of("test.txt");

    @AfterAll
    public static void deleteFile() throws IOException {
        Files.deleteIfExists(PATH_OF_FILE);
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
                .hasMessage("Exception in converting arguments");
    }

    @Test
    void main_invalidSecondArgument() {
        String[] arguments = new String[]{"100", null};
        assertThatCode(() -> Main.main(arguments)).isInstanceOf(ArgumentsException.class)
                .hasMessage("Exception in converting arguments");
    }

    @Test
    void main_successfulExecution() {
        String[] arguments = new String[]{"100", PATH_OF_FILE.toString()};
        assertThatCode(() -> Main.main(arguments)).doesNotThrowAnyException();
        assertTrue(Files.exists(PATH_OF_FILE), "The file was not created.");
    }
}
