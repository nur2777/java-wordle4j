package ru.yandex.practicum;

import java.io.PrintWriter;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {

    /**
     * Правильный ответ загаданный в игре
     */
    private final String rightAnswer;
    /**
     * Номер текущего шага
     */
    private int currentStep;
    /**
     * Словарь правильных слов
     */
    private final WordleDictionary dictionary;
    /**
     * Максимальное количество попыток
     */
    private final int maxSteps = 6;
    /**
     * Длина правильного слова
     */
    private static final int wordLength = 5;
    /**
     * Статус игры
     */
    private WordleGameStatus gameStatus;

    /**
     * Лог файл
     */
    private final PrintWriter logFile;

    public int getMaxSteps() {
        return maxSteps;
    }

    public static int getWordLength() {
        return wordLength;
    }

    public WordleGame (WordleDictionary dictionary, PrintWriter logFile) {
        this.dictionary = dictionary;
        this.logFile = logFile;
        this.currentStep = 1;
        this.rightAnswer = dictionary.getRandomWord();
        this.gameStatus = WordleGameStatus.IN_PROGRESS;
    }

    public WordleGameStatus getGameStatus() {
        return gameStatus;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    /** Метод анализирует ответ пользователя и возвращает результат
     * @param userAnswer слово введенное пользователем
     * @return строку с посимвольным результатом сравнения
     */
    public String compareUserAnswer(String userAnswer) {
        userAnswer = userAnswer.toLowerCase().replace("ё","e");
        if (rightAnswer.equals(userAnswer)) {
            gameStatus = WordleGameStatus.SUCCESS;
        } else {
            currentStep++;
            if (currentStep > maxSteps) {
                gameStatus = WordleGameStatus.FAIL;
            }
        }
        return getResult(userAnswer);
    }

    /** Метод выполняет проверки введенного пользователем слова согласно ТЗ
     *  Проверять длину слова, пустоту и наличие в словаре
     * @param word введенное пользователем слово
     * @return true - если все проверки успешно пройдены, false - если проверки не пройдены
     */
    public boolean checkWord(String word) {
        logFile.println("Введено слово: " + word);
        if (word.isBlank() || (word.length() != wordLength)) {
            logFile.println("Слово либо не соответствует длине (" + wordLength + "), либо содержит только пробелы. Проверка не пройдена!");
            return false;
        }
        if (!dictionary.getWords().contains(word)) {
            logFile.println("Данное слово отсутствует в словаре. Проверка не пройдена!");
            return false;
        }
        logFile.println("Проверки пройдены успешно.");
        return true;
    }

    /** Метод сравнивает посимвольно правильный ответ и ответ пользователя
     * @param userAnswer слово введенное пользователем
     * @return результат сравнения согласно правилам игры
     */
    private String getResult(String userAnswer) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < rightAnswer.length(); i++) {
            if (userAnswer.charAt(i) == rightAnswer.charAt(i)) {
                result.append("+");
            } else if (rightAnswer.contains(String.valueOf(userAnswer.charAt(i)))) {
                result.append("^");
            } else {
                result.append("-");
            }
        }
        return result.toString();
    }
}
