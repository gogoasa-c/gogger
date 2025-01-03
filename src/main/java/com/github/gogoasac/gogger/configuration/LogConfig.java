package com.github.gogoasac.gogger.configuration;

import com.github.gogoasac.gogger.Logger;
import com.github.gogoasac.gogger.logstructure.LogStructure;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogConfig {
    @Value("${spring.log-level:INFO}")
    private String logLevel;

    @Bean
    public Logger logger(LogStructure logStructure) {
        return new Logger(System.out, Logger.class, logStructure);
    }
}
