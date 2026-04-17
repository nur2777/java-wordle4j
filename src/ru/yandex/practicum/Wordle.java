package ru.yandex.practicum;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
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
            gameProcess(game);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Программа завершена. Спасибо за игру!");
        }

    }

    private static void gameProcess(WordleGame game) {
        Scanner scanner = new Scanner(System.in);
        //String hint;
        while (game.getGameStatus() == WordleGameStatus.IN_PROGRESS) {
            System.out.println("Попытка № " + game.getCurrentStep() + ". Введите слово и нажмите Enter");
            String userAnswer = scanner.nextLine();
            while (!game.checkWord(userAnswer)) {
                System.out.println("Неверное слово! Введите слово повторно и нажмите Enter");
                userAnswer = scanner.nextLine();
            }
            String result = game.compareUserAnswer(userAnswer);
            System.out.println(result);
        }
        if (game.getGameStatus() == WordleGameStatus.SUCCESS) {
            System.out.println("Поздравляем! Вы угадали слово!");
        } else if (game.getGameStatus() == WordleGameStatus.FAIL) {
            System.out.println("К сожалению, вы проиграли! Но вы можете попробовать ещё раз.");
        }

    }

}
