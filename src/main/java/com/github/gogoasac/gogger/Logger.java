package com.github.gogoasac.gogger;

import com.github.gogoasac.gogger.logging.LogLevel;
import com.github.gogoasac.gogger.logging.LoggableMessage;
import com.github.gogoasac.gogger.logging.logstructure.LogStructure;
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
    private static LogLevel applicationLogLevel;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) {
        context = applicationContext;
        applicationLogLevel = LogLevel.valueOf(applicationContext.getBean("logLevel", String.class));
    }

    @NotNull
    public static Logger init(Class<?> clazz) {
        LogStructure logStructure = context.getBean(LogStructure.class);
        return new Logger(System.out, clazz, logStructure);
    }

    @NotNull
    public static Logger init(Class<?> clazz, LogStructure logStructure) {
        return new Logger(System.out, clazz, logStructure);
    }

    public void trace(String message) {
        this.log(new LoggableMessage(message, LogLevel.TRACE));
    }

    public void debug(String message) {
        this.log(new LoggableMessage(message, LogLevel.DEBUG));
    }

    public void info(String message) {
        this.log(new LoggableMessage(message, LogLevel.INFO));
    }

    public void warn(String message) {
        this.log(new LoggableMessage(message, LogLevel.WARN));
    }

    public void error(String message) {
        this.log(new LoggableMessage(message, LogLevel.ERROR));
    }

    private void log(LoggableMessage loggableMessage) {
        if (loggableMessage.logLevel().getLevel() < applicationLogLevel.getLevel()) {
            return;
        }

        handleWrite(() -> outputStream.write(formatLogMessage(loggableMessage).getBytes()));
    }

    @NotNull
    private String formatLogMessage(LoggableMessage loggableMessage) {
        return String.format("%s:\t %s%n", this.logStructure.getLogPattern(), loggableMessage.message())
                .replace(Constant.TIMESTAMP, LocalDateTime.now().toString())
                .replace(Constant.CLASS_NAME, this.clazz.getName())
                .replace(Constant.LOG_LEVEL, loggableMessage.logLevel().getValue());
    }

    private void handleWrite(ThrowingRunnable action) {
        try {
            action.run();
        } catch (Exception exception) {
            System.err.printf("Encountered exception: %s\n", exception.getMessage());
        }
    }
}
