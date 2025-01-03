package com.github.gogoasac.gogger.logstructure;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class LogStructure {
    @Value("${spring.logging.log-pattern:[{TIMESTAMP} - {LOG_LEVEL}] [{CLASS_NAME}]:")
    private String logPattern;
}
