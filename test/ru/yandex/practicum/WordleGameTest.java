package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.Exceptions.WordHasNotCirilicChar;
import ru.yandex.practicum.Exceptions.WordNotFoundInDictionary;
import ru.yandex.practicum.Exceptions.WordNullOrIncorrectLength;
import ru.yandex.practicum.Exceptions.WordleGameExceptions;

import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;

class WordleGameTest {
    private static WordleGame game;

    @BeforeAll
    public static void loadDictionary() {
        try {
            WordleDictionaryLoader dictionaryLoader = new WordleDictionaryLoader("words_ru.txt",
                    "UTF8", new PrintWriter(System.out));
            WordleDictionary dictionary = dictionaryLoader.loadDictionary();
            game = new WordleGame(dictionary,"город", new PrintWriter(System.out));
        } catch (WordleGameExceptions | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void testCompareUserAnswer() {
        String result = game.compareUserAnswer("ветер");
        assertEquals("----^",result,"Неверная логика сравнения слов для слова с одним существующим " +
                "символом");
    }

    @Test
    void testCompareUserAnswerRight() {
        String result = game.compareUserAnswer("город");
        assertEquals("+++++",result,"Неверная логика сравнения слов для правильного варианта");
    }

    @Test
    void testCheckWordLength() {
        boolean checkRes = false;
        try {
            game.checkWord("привет");
        } catch (WordNullOrIncorrectLength e) {
            checkRes = true;
        } catch (WordNotFoundInDictionary | WordHasNotCirilicChar ignored) {
        }

        assertTrue(checkRes,"Неверная логика работа метода проверки слов на длинну");

        try {
            game.checkWord("hello");
        } catch (WordHasNotCirilicChar e) {
            checkRes = true;
        } catch (WordNotFoundInDictionary | WordNullOrIncorrectLength ignored) {

        }
        assertTrue(checkRes,"Неверная логика работа метода проверки слов (не кирилица)");

    }

    @Test
    void testCheckWordCirilicChar() {
        boolean checkRes = false;

        try {
            game.checkWord("hello");
        } catch (WordHasNotCirilicChar e) {
            checkRes = true;
        } catch (WordNotFoundInDictionary | WordNullOrIncorrectLength ignored) {
        }
        assertTrue(checkRes,"Неверная логика работа метода проверки слов (не кириллица)");
    }


    @Test
    void testCheckWordNotFoundInDictionary() {
        boolean checkRes = false;

        try {
            game.checkWord("дабро");
        } catch (WordNotFoundInDictionary e) {
            checkRes = true;
        } catch (WordHasNotCirilicChar | WordNullOrIncorrectLength ignored) {
        }
        assertTrue(checkRes,"Неверная логика работа метода проверки слов (отсутствует в словаре)");
    }

    @Test
    void testGetHintLength() {
        String hint = game.getHint();
        assertEquals(5, hint.length(),"Неверная длинна слова подсказки");
    }
}