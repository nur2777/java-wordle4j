package ru.yandex.practicum.Exceptions;

import java.io.PrintWriter;

public class WordNotFoundInDictionary extends Exception {
    public WordNotFoundInDictionary() {
        super("Данное слово отсутствует в словаре.");
    }
}
