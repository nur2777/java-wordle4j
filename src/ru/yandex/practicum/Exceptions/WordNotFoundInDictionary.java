package ru.yandex.practicum.Exceptions;

public class WordNotFoundInDictionary extends Exception {
    public WordNotFoundInDictionary() {
        super("Данное слово отсутствует в словаре.");
    }
}
