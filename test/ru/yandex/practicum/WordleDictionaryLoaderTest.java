package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.Exceptions.WordleGameExceptions;

import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryLoaderTest {

    private WordleDictionary dictionary = new WordleDictionary();

    private void loadDictionaryFromFile(String filename) {
        try {
            WordleDictionaryLoader dictionaryLoader = new WordleDictionaryLoader(filename,
                    "UTF8", new PrintWriter(System.out));
            dictionary = dictionaryLoader.loadDictionary();
        } catch (WordleGameExceptions | IOException e) {
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void testLoadEmptyDictionary() throws IOException {
        loadDictionaryFromFile("words_empty.txt");
        assertTrue(dictionary.isEmpty(), "Неверное состояние словаря при пустом файле!");
    }

    @Test
    public void testLoadDictionaryFromNotExistFile() throws IOException {
        loadDictionaryFromFile("words_not_exists.txt");
        assertTrue(dictionary.isEmpty(), "Неверное состояние словаря при несуществующем файле!");
    }

    @Test
    public void testFullLoadDictionary() throws IOException {
        loadDictionaryFromFile("words_ru.txt");
        assertEquals(4159,dictionary.size(), "Неверное количество слов загружено!");
    }
}