package org.geekhub.hw5;

import org.geekhub.hw5.exception.FileException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class FileUtils {

    private FileUtils() {

    }

    public static List<String> readAllLines(String file) {
        try {
            return Files.readAllLines(Path.of(file), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new FileException(e.getMessage());
        }
    }

    public static void createDirectories(Path path) {
        try {
            Files.createDirectories(path);

        } catch (IOException e) {
            throw new FileException(e.getMessage());
        }
    }

    public static void writeToFile(Path path, byte[] content) {
        try {
            Files.write(path, content);
        }
        catch(IOException e){
            throw new FileException(e.getMessage());
        }
    }

    public static void copyToFile(InputStream inputStream, Path path) {
        try {
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        }
        catch(IOException e){
            throw new FileException(e.getMessage());
        }
    }

    public static void createFileIfNotExists(Path path) {
        try {
            if (Files.notExists(path)) {
                Files.createFile(path);
            }
        }
        catch(IOException e){
            throw new FileException(e.getMessage());
        }
    }

    public static void deleteDirectories(String directory) {
        try {
            Path pathToBeDeleted = Path.of(directory);
            Files.walkFileTree(pathToBeDeleted,
                new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult postVisitDirectory(
                        Path dir, IOException exc) throws IOException {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(
                        Path file, BasicFileAttributes attrs)
                        throws IOException {
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }
                });
        }
        catch(IOException e){
            throw new FileException(e.getMessage());
        }
    }

    public static void deleteIfExists(Path path) {
        try {
            Files.deleteIfExists(path);
        }
        catch (IOException e){
            throw new FileException(e.getMessage());
        }
    }
}
