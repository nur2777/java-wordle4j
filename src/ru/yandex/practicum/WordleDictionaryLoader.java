package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Класс предназначен для загрузки слов из файла
 *  и получения основного словаря для игры
 */
public class WordleDictionaryLoader {

    /**
     * Имя исходного файла со словами
     */
    private final String sourceFileName;
    /**
     * Кодировка исходного файла
     */
    private final Charset charset;

    /**
     * Лог файл
     */
    private final PrintWriter logFile;

    public WordleDictionaryLoader(String sourceFileName, String charsetName, PrintWriter logFile) {
        this.sourceFileName = sourceFileName;
        this.charset = Charset.forName(charsetName);
        this.logFile = logFile;
    }

    /** Метод считывает данные из исходного файла и заполняет словарь для игры
     * @return заполненный словарь для игры
     */
    public WordleDictionary loadDictionary() throws IOException {
        WordleDictionary dictionary = new WordleDictionary();
        logFile.println("Шаг первый - загрузка словаря");
        try (Reader fileReader = new FileReader(sourceFileName,charset)) {
            BufferedReader buffer = new BufferedReader(fileReader);
            while (buffer.ready()) {
                String word = buffer.readLine();
                if (word.length() == WordleGame.getWordLength()) dictionary.add(word);
            }
            logFile.println("Загрузка словаря успешно завершена.");
        } catch (FileNotFoundException e) {
            logFile.println("Исходный файл " + sourceFileName + " для загрузки не найден.");
        }
        return dictionary;
    }
}
