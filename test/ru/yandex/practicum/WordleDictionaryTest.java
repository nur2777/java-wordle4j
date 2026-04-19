package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.Exceptions.WordleGameExceptions;

import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryTest {
    private static WordleDictionary dictionary = new WordleDictionary();

    @BeforeAll
    public static void loadDictionary() {
        try {
            WordleDictionaryLoader dictionaryLoader = new WordleDictionaryLoader("words_ru.txt",
                    "UTF8", new PrintWriter(System.out));
            dictionary = dictionaryLoader.loadDictionary();
        } catch (WordleGameExceptions | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void testGetRandomWordLength() {
        String randomWord = dictionary.getRandomWord();
        assertEquals(5,randomWord.length(),"Неверная длинна слова у случайного слова");
    }

    @Test
    void testDeleteWordWithChar() {
        dictionary.deleteWordWithChar("о");
        assertFalse(dictionary.getWords().contains("закон"),"Некорректная работа метода удаления слов по букве");
    }

    @Test
    void testDeleteWordNotContainsThisChar() {
        dictionary.deleteWordNotContainsThisChar("р");
        assertFalse(dictionary.getWords().contains("забег"),"Некорректная работа метода удаления слов " +
                "не содержащих указанную букву");
    }

    @Test
    void deleteWordCharOnPosition() {
        dictionary.deleteWordCharOnPosition('г',4);
        assertFalse(dictionary.getWords().contains("забег"),"Некорректная работа метода удаления слова " +
                "с буквой на указанной позиции");
    }

    @Test
    void deleteWordCharNotOnPosition() {
        dictionary.deleteWordCharNotOnPosition('в',0);
        assertTrue(dictionary.getWords().contains("ветер"),"Некорректная работа метода удаления слов " +
                "не содержащих букву на указанной позиции");
    }

    @Test
    void remove() {
        dictionary.remove("город");
        assertFalse(dictionary.getWords().contains("город"),"Некорректная работа метода удаления слов");
    }

    @Test
    void isEmpty() {
        dictionary.clear();
        assertTrue(dictionary.isEmpty(),"Некорректная работа метода проверки пустоты");
    }
}