package ru.yandex.practicum.Exceptions;

import java.io.PrintWriter;

public class WordHasNotCirilicChar extends Exception {
    public WordHasNotCirilicChar() {
        super("Слово содержит символ не из кириллицы. Оно должно содержать только кириллические символы.");
    }
}
