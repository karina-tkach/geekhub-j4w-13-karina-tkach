package org.geekhub.hw6;

import com.google.gson.Gson;
import org.apache.http.impl.client.HttpClientBuilder;
import org.geekhub.hw6.exception.ArgumentsException;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        if (args.length != 2) {
            throw new ArgumentsException("Both time interval and path should be provided via command-line arguments");
        }
        int timeInterval;
        Path path;

        try {
            timeInterval = Integer.parseInt(args[0]);
            path = Path.of(args[1]);
        }
        catch(NumberFormatException e){
            throw new ArgumentsException("First argument must be numeric value", e);
        }
        catch (InvalidPathException e) {
            throw new ArgumentsException("Second argument cannot be converted to a Path", e);
        }
        var httpClient = HttpClientBuilder.create().build();
        var catFactService = new CatFactService(httpClient, new Gson());
        CatFactWriter catFactWriter = new CatFactWriter(catFactService, timeInterval, path);
        catFactWriter.writeFactsToFile();
    }
}
