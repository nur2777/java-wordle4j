package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

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

    public WordleDictionary (List<String> array) {
        this.words = new ArrayList<>(array);
    }

    public List<String> getWords() {
        return words;
    }

    /** Метод добавляет слова в словарь
     * @param word слово для добавления
     */
    public void add (String word) {
        words.add(normalize(word));
    }

    /** Метод удаляет слово из словаря
     * @param word слово для удаления
     */
    public void remove (String word) {
        words.remove(word);
    }

    /** Метод проверяет словарь на пустоту
     */
    public boolean isEmpty () {
        return words.isEmpty();
    }

    /** Метод проверяет словарь на пустоту
     */
    public int size () {
        return words.size();
    }

    /** Метод очищает словарь
     */
    public void clear () {
        words.clear();
    }

    /** Метод нормализации слова по правилам игры
     * @param word слово для нормализации
     * @return нормализованное слово
     */
    public String normalize (String word) {
        // нужно привести слова к единой форме в нижнем регистре, без пробелов в начале/конце
        // и заменить букву ё на букву е, потому что в игре они равнозначны.
        return word.toLowerCase().trim().replace("ё","e");
    }

    /** Метод возвращает случайное слово из словаря
     * @return случайное слово
     */
    protected String getRandomWord() {
        int randomInt = new Random().nextInt(words.size());
        return words.get(randomInt);
    }

    /** Метод удаляет слова из словаря в которых есть указанная буква
     * @param character буква которой не должно быть в слове
     */
    protected void deleteWordWithChar(CharSequence character) {
        Predicate<String> wordsWithoutChar = str -> str.contains(character);
        words.removeIf(wordsWithoutChar);
    }

    /** Метод удаляет слова из словаря в которых нет указанной буквы
     * @param character буква которая должно быть в слове
     */
    protected void deleteWordNotContainsThisChar(CharSequence character) {
        Predicate<String> wordsWithoutChar = str -> !(str.contains(character));
        words.removeIf(wordsWithoutChar);
    }
    /** Метод удаляет слова из словаря, где указанная буква на заданной позиции
     * @param character буква которой не должно быть в слове словаря
     */
    protected void deleteWordCharOnPosition(char character, int i) {
        Predicate<String> wordsWithoutChar = str -> (str.charAt(i) == character);
        words.removeIf(wordsWithoutChar);
    }

    /** Метод удаляет слова из словаря в которых нет указанной буквы на заданной позиции
     * @param character буква которой не должно быть в слове словаря
     */
    protected void deleteWordCharNotOnPosition(char character, int i) {
        Predicate<String> wordsWithoutChar = str -> !(str.charAt(i) == character);
        words.removeIf(wordsWithoutChar);
    }
}
