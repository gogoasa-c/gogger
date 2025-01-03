package com.github.gogoasac.gogger;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LogLevel {
    TRACE("TRACE", 0),
    DEBUG("DEBUG", 1),
    INFO("INFO", 2),
    WARN("WARN", 3),
    ERROR("ERROR", 4);

    private final String value;
    private final int level;
}
