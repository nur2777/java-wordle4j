package ru.yandex.practicum;

import ru.yandex.practicum.Exceptions.WordleGameExceptions;

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
    public WordleDictionary loadDictionary() throws IOException, WordleGameExceptions {
        WordleDictionary dictionary = new WordleDictionary();
        logFile.println("Шаг первый - загрузка словаря");
        try (Reader fileReader = new FileReader(sourceFileName,charset)) {
            BufferedReader buffer = new BufferedReader(fileReader);
            while (buffer.ready()) {
                String word = buffer.readLine();
                if (word.length() == WordleGame.getWordLength()) {
                    dictionary.add(word);
                }
            }
            if (!dictionary.getWords().isEmpty()) {
                logFile.println("Загрузка словаря успешно завершена. Загружено " + dictionary.size() + " слов");
            } else {
                throw new WordleGameExceptions("Ошибка при загрузке словаря. Словарь пуст, ни одно " +
                        "слово не загружено.", logFile);
            }
        } catch (FileNotFoundException e) {
            throw new WordleGameExceptions("Исходный файл словаря " + sourceFileName + " не найден. " +
                    "Загрузка прервана!", logFile);
        }
        return dictionary;
    }
}
