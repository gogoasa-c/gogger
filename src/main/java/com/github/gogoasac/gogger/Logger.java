package com.github.gogoasac.gogger;

import com.github.gogoasac.gogger.logstructure.LogStructure;
import com.github.gogoasac.gogger.logstructure.StandardLogStructure;
import com.github.gogoasac.gogger.util.ThrowingRunnable;
import lombok.RequiredArgsConstructor;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class Logger {
    private final OutputStream outputStream;
    private final Class<?> clazz;
    private final LogStructure logStructure = new StandardLogStructure();

    public static Logger init(Class<?> clazz) {
        return new Logger(System.out, clazz);
    }

    public void info(String message) {
       handleWrite(() -> outputStream.write(message.getBytes(StandardCharsets.UTF_8)));
    }

    private void handleWrite(ThrowingRunnable action) {
        try {
            action.run();
        } catch (Exception exception) {
            System.err.printf("Encountered exception: %s\n", exception.getMessage());
        }
    }
}
