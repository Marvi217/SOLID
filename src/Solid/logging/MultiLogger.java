package Solid.logging;

import java.util.List;

public class MultiLogger implements Logger {
    private final List<Logger> loggers;

    public MultiLogger(List<Logger> loggers) {
        this.loggers = loggers;
    }

    @Override
    public void info(String message) {
        loggers.forEach(l -> l.info(message));
    }

    @Override
    public void error(String message) {
        loggers.forEach(l -> l.error(message));
    }

    @Override
    public void warn(String message) {
        loggers.forEach(l -> l.warn(message));
    }
}