package com.github.gogoasac.gogger.logstructure;

import com.github.gogoasac.gogger.util.Constant;

public class StandardLogStructure implements LogStructure {

    @Override
    public String getStructure() {
        return "[%s - %s] [%s]: ".formatted(Constant.TIMESTAMP, Constant.LOG_LEVEL, Constant.LOG_LEVEL);
    }
}
