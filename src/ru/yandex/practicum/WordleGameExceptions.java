package ru.yandex.practicum;

import java.io.PrintWriter;

public class WordleGameExceptions extends RuntimeException {

    public WordleGameExceptions(String message, PrintWriter logFile) {
        super(message);
        logFile.println(message);
    }
}
