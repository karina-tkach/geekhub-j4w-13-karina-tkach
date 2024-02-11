package org.geekhub.hw5;

import org.geekhub.hw5.exception.FileException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class FileUtils {

    private FileUtils() {

    }

    public static List<String> readAllLines(String file) {
        try {
            return Files.readAllLines(Path.of(file), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new FileException("Error happened while reading lines from file");
        }
    }

    public static void createDirectories(Path path) {
        try {
            Files.createDirectories(path);

        } catch (IOException e) {
            throw new FileException("Error happened while creating directories");
        }
    }

    public static void writeToFile(Path path, byte[] content) {
        try {
            Files.write(path, content);
        } catch (IOException e) {
            throw new FileException("Error happened while writing to file");
        }
    }

    public static void copyToFile(InputStream inputStream, Path path) {
        try {
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileException("Error happened while copying file");
        }
    }

    public static void createFileIfNotExists(Path path) {
        try {
            if (Files.notExists(path)) {
                Files.createFile(path);
            }
        } catch (IOException e) {
            throw new FileException("Error happened while creating file");
        }
    }

    public static void deleteDirectories(String directory) {
        try (Stream<Path> pathStream = Files.walk(Path.of(directory))) {
            pathStream
                .sorted(Comparator.reverseOrder())
                .forEach(FileUtils::deleteIfExists);
        } catch (IOException e) {
            throw new FileException("Error happened while deleting directories", e);
        }
    }

    public static void deleteIfExists(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new FileException("Error happened while deleting file or directory");
        }
    }
}
