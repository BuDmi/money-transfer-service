package ru.netology.moneytransfer.logger;

import java.io.IOException;

import static ru.netology.moneytransfer.util.FileUtils.createNewFile;
import static ru.netology.moneytransfer.util.FileUtils.writeTextToFile;

public class TextFileLogger implements Logger {

    private final String path;
    private final String file;

    public TextFileLogger(String path, String file) {
        this.path = path;
        this.file = file;
        createNewFile(path, file);
        logNewMessage("---New session---");
    }

    @Override
    public void logNewMessage(String message) {
        try {
            writeTextToFile(path, file, message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
