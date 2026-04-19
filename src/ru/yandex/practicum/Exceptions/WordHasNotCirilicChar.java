package ru.yandex.practicum.Exceptions;

public class WordHasNotCirilicChar extends Exception {
    public WordHasNotCirilicChar() {
        super("Слово содержит символ не из кириллицы. Оно должно содержать только кириллические символы.");
    }
}
