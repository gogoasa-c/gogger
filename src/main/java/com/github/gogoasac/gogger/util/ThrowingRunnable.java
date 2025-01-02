package com.github.gogoasac.gogger.util;

@FunctionalInterface
public interface ThrowingRunnable {
    void run() throws Exception;
}
