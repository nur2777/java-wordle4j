package ru.yandex.practicum;

import ru.yandex.practicum.Exceptions.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Wordle {

    /**
     * Имя лог-файла
     */
    private static final String logFileName = "log.txt";
    /**
     * Имя исходного файла со списком слов
     */
    private static final String sourceFileName = "words_ru.txt";
    /**
     * Кодировка файла со списком слов и лог файла
     */
    private static final String charset = "UTF8";

    public static void main(String[] args) {
        try (PrintWriter logFile = new PrintWriter(logFileName, charset)) {
            System.out.println("Добро пожаловать в игру Wordle!");
            WordleDictionaryLoader dictionaryLoader = new WordleDictionaryLoader(sourceFileName,charset,logFile);
            WordleDictionary dictionary = dictionaryLoader.loadDictionary();
            WordleGame game = new WordleGame(dictionary,logFile);
            System.out.println("Угадайте загаданное слово из " + WordleGame.getWordLength() +" букв за " + game.getMaxSteps() + " попыток!");
            gameProcess(game,logFile);
        } catch (WordleGameExceptions | IOException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Программа завершена. Спасибо за игру!");
        }

    }

    /** Метод в котором реализован процесс игры - основной игровой цикл
     * @param game созданная игра
     */
    private static void gameProcess(WordleGame game,PrintWriter logFile) {
        Scanner scanner = new Scanner(System.in);
        while (game.getGameStatus() == WordleGameStatus.IN_PROGRESS) {
            try {
                logFile.println("-".repeat(20) + ". Попытка: " + game.getCurrentStep());
                System.out.println("Попытка № " + game.getCurrentStep() + ". Введите слово и нажмите Enter");
                String userAnswer = scanner.nextLine().trim();
                if (userAnswer.isBlank()) { // если просто нажали Enter без ввода слова
                    logFile.println("Пользователь нажал Enter");
                    userAnswer = game.getHint(); // выдаём подсказку
                    System.out.println(userAnswer);
                }
                game.checkWord(userAnswer);
                String result = game.compareUserAnswer(userAnswer);
                logFile.println("Строка-подсказка:" + result);
                System.out.println(result);
            } catch (WordNotFoundInDictionary | WordNullOrIncorrectLength | WordHasNotCirilicChar exception) {
                System.out.println(exception.getMessage());
                logFile.println(exception.getMessage());
            }
        }
        if (game.getGameStatus() == WordleGameStatus.SUCCESS) {
            System.out.println("Поздравляем! Вы выиграли и угадали слово!");
        } else if (game.getGameStatus() == WordleGameStatus.FAIL) {
            System.out.println("К сожалению, вы проиграли! Было загадано слово: " + game.getRightAnswer()+ ". Вы можете попробовать ещё раз.");
        }

    }

}
