package Solid.logging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConsoleLogger implements Logger {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void info(String message) {
        System.out.println("[" + dtf.format(LocalDateTime.now()) + "] INFO: " + message);
    }

    @Override
    public void error(String message) {
        System.err.println("[" + dtf.format(LocalDateTime.now()) + "] ERROR: " + message);
    }

    @Override
    public void warn(String message) {
        System.out.println("[" + dtf.format(LocalDateTime.now()) + "] WARN: " + message);
    }
}