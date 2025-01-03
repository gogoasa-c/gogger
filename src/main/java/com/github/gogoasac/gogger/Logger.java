package com.github.gogoasac.gogger;

import com.github.gogoasac.gogger.logstructure.LogStructure;
import com.github.gogoasac.gogger.util.Constant;
import com.github.gogoasac.gogger.util.ThrowingRunnable;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.OutputStream;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class Logger implements ApplicationContextAware {
    private final OutputStream outputStream;
    private final Class<?> clazz;
    private final LogStructure logStructure;

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) {
        context = applicationContext;
    }

    public static Logger init(Class<?> clazz) {
        LogStructure logStructure = context.getBean(LogStructure.class);
        return new Logger(System.out, clazz, logStructure);
    }

    public static Logger init(Class<?> clazz, LogStructure logStructure) {
        return new Logger(System.out, clazz, logStructure);
    }

    public void info(String message) {
       handleWrite(() -> outputStream.write(formatLogMessage(message).getBytes()));
    }

    public String formatLogMessage(String message) {
       return String.format("%s %s", this.logStructure.getLogPattern(), message)
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
