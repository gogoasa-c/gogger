package com.github.gogoasac.gogger;

import com.github.gogoasac.gogger.logstructure.LogStructure;
import com.github.gogoasac.gogger.util.Constant;
import com.github.gogoasac.gogger.util.ThrowingRunnable;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
    private static LogLevel applicationLogLevel;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) {
        context = applicationContext;
        applicationLogLevel = LogLevel.valueOf(applicationContext.getBean("logLevel", String.class));
    }

    public static Logger init(Class<?> clazz) {
        LogStructure logStructure = context.getBean(LogStructure.class);
        return new Logger(System.out, clazz, logStructure);
    }

    public static Logger init(Class<?> clazz, LogStructure logStructure) {
        return new Logger(System.out, clazz, logStructure);
    }

    public void trace(String message) {
        this.log(message, LogLevel.TRACE);
    }

    public void debug(String message) {
        this.log(message, LogLevel.DEBUG);
    }

    public void info(String message) {
        this.log(message, LogLevel.INFO);
    }

    public void warn(String message) {
        this.log(message, LogLevel.WARN);
    }

    public void error(String message) {
        this.log(message, LogLevel.ERROR);
    }

    private void log(String message, LogLevel logLevel) {
        if (logLevel.getLevel() < applicationLogLevel.getLevel()) {
            return;
        }

        handleWrite(() -> outputStream.write(formatLogMessage(message, logLevel).getBytes()));
    }

    private String formatLogMessage(String message, LogLevel logLevel) {
        return String.format("%s:\t %s%n", this.logStructure.getLogPattern(), message)
                .replace(Constant.TIMESTAMP, LocalDateTime.now().toString())
                .replace(Constant.CLASS_NAME, this.clazz.getName())
                .replace(Constant.LOG_LEVEL, logLevel.getValue());
    }

    private void handleWrite(ThrowingRunnable action) {
        try {
            action.run();
        } catch (Exception exception) {
            System.err.printf("Encountered exception: %s\n", exception.getMessage());
        }
    }
}
