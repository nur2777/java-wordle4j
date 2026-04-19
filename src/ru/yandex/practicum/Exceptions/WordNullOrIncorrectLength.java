package ru.yandex.practicum.Exceptions;

public class WordNullOrIncorrectLength extends RuntimeException {
    public WordNullOrIncorrectLength(String message) {
        super(message);
    }
}
