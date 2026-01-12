package Solid.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileLogger implements Logger {
    private final String fileName;

    public FileLogger(String fileName) {
        this.fileName = fileName;
    }

    private void logToFile(String level, String message) {
        try (FileWriter fw = new FileWriter(fileName, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(level + ": " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void info(String message) { logToFile("INFO", message); }

    @Override
    public void error(String message) { logToFile("ERROR", message); }

    @Override
    public void warn(String message) { logToFile("WARN", message); }
}