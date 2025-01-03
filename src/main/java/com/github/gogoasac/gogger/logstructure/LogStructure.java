package com.github.gogoasac.gogger.logstructure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class LogStructure {
    @Value("${spring.logging.log-pattern:[{TIMESTAMP} - {LOG_LEVEL}] [{CLASS_NAME}]}")
    private String logPattern;
}
