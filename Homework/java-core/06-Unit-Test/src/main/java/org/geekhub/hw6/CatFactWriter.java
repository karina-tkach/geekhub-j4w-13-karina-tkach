package org.geekhub.hw6;

import org.geekhub.hw6.exception.CatFactException;
import org.geekhub.hw6.exception.FileException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class CatFactWriter {
    private final CatFactService catFactService;
    private final int timeInterval;
    private final Path filePath;

    public CatFactWriter(CatFactService catFactService, int timeInterval, Path filePath) {
        this.catFactService = catFactService;
        this.timeInterval = timeInterval;
        this.filePath = filePath;
    }

    private void createFile() {
        try {
            Files.deleteIfExists(filePath);
            Files.createFile(filePath);
        } catch (IOException e) {
            throw new FileException(String.format("Fail to create file with provided path: %s", filePath), e);
        }
    }

    private String getCatFactWrapper() {
        try {
            return catFactService.getRandomCatFact();
        } catch (CatFactException e) {
            return null;
        }
    }

    public void writeFactsToFile() throws InterruptedException {
        createFile();
        List<String> facts = new ArrayList<>();
        int currentNumberOfRetries = 0;
        final int MAX_NUMBER_OF_RETRIES = 5;
        try {
            while (currentNumberOfRetries <= MAX_NUMBER_OF_RETRIES) {
                String catFact = getCatFactWrapper();
                if (catFact != null && !facts.contains(catFact)) {
                    currentNumberOfRetries = 0;
                    facts.add(catFact);
                    Files.writeString(filePath, String.format("%s%n", catFact), StandardOpenOption.APPEND);
                    Thread.sleep(timeInterval);
                } else {
                    currentNumberOfRetries++;
                }
            }

            Files.writeString(filePath, "I don't know", StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new FileException(String.format("Fail to write string to file %s", filePath), e);
        }
    }

}
