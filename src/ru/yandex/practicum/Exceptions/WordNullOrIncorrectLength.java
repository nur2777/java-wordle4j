package ru.yandex.practicum.Exceptions;

import java.io.PrintWriter;

public class WordNullOrIncorrectLength extends Exception {
    public WordNullOrIncorrectLength(String message) {
        super(message);
    }
}
