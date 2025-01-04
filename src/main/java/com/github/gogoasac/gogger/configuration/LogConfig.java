package com.github.gogoasac.gogger.configuration;

import com.github.gogoasac.gogger.Logger;
import com.github.gogoasac.gogger.logging.logstructure.LogStructure;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class LogConfig {
    @Value("${logging.gogger.log-level:INFO}")
    private String logLevel;

    @Bean
    public String logLevel() {
        return logLevel;
    }

    @Bean
    public Logger logger(LogStructure logStructure) {
        return new Logger(Logger.class, logStructure);
    }

}
