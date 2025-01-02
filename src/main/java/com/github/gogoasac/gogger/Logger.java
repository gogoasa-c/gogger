package com.github.gogoasac.gogger;

import com.github.gogoasac.gogger.logstructure.LogStructure;
import com.github.gogoasac.gogger.logstructure.StandardLogStructure;
import com.github.gogoasac.gogger.util.Constant;
import com.github.gogoasac.gogger.util.ThrowingRunnable;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private final OutputStream outputStream;
    private final Class<?> clazz;
    private LogStructure logStructure = new StandardLogStructure();

    public Logger(final OutputStream outputStream, final Class<?> clazz) {
        this.outputStream = outputStream;
        this.clazz = clazz;
    }

    public static Logger init(Class<?> clazz) {
        return new Logger(System.out, clazz);
    }

    public void info(String message) {
       handleWrite(() -> outputStream.write(formatLogMessage(message).getBytes()));
    }

    public String formatLogMessage(String message) {
       return String.format("%s %s", this.logStructure.getStructure(), message)
               .replace(Constant.TIMESTAMP, LocalDateTime.now().toString())
               .replace(Constant.CLASS_NAME, this.clazz.getName())
               .replace(Constant.LOG_LEVEL, "INFO");
    }

    private void handleWrite(ThrowingRunnable action) {
        try {
            action.run();
        } catch (Exception exception) {
            System.err.printf("Encountered exception: %s\n", exception.getMessage());
        }
    }
}
