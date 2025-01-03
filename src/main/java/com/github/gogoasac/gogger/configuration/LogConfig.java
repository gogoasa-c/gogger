package com.github.gogoasac.gogger.configuration;

import com.github.gogoasac.gogger.Logger;
import com.github.gogoasac.gogger.logstructure.LogStructure;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogConfig {
    @Bean
    public Logger logger(LogStructure logStructure) {
        return new Logger(System.out, Logger.class, logStructure);
    }
}
