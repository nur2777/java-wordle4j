package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Класс предназначен для хранения списка слов words
 * и методы работы с этим списком
 */
public class WordleDictionary {
    /**
     * Основной список слов для игры
     */
    private final List<String> words;

    public WordleDictionary () {
        this.words = new ArrayList<>();
    }

    public List<String> getWords() {
        return words;
    }

    /** Метод добавляет слова в словарь
     * @param word слово для добавления
     */
    public void add (String word) {
        // нужно привести слова к единой форме в нижнем регистре и заменить букву ё на букву е,
        // потому что в игре они равнозначны.
        words.add(word.toLowerCase().replace("ё","e"));
    }

    /** Метод возвращает случайное слово из словаря
     * @return случайное слово
     */
    protected String getRandomWord() {
        int randomInt = new Random().nextInt(words.size());
        return words.get(randomInt);
    }
    /*
    этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
}
