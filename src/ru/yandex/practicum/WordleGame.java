package ru.yandex.practicum;

import ru.yandex.practicum.Exceptions.*;

import java.io.PrintWriter;

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
     * Словарь подсказок
     */
    private final WordleDictionary hints;
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

    public String getRightAnswer() {
        return rightAnswer;
    }

    public WordleGameStatus getGameStatus() {
        return gameStatus;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public WordleGame (WordleDictionary dictionary, PrintWriter logFile) {
        this.dictionary = dictionary;
        this.logFile = logFile;
        this.currentStep = 1;
        this.rightAnswer = dictionary.getRandomWord();
        this.gameStatus = WordleGameStatus.IN_PROGRESS;
        this.hints = new WordleDictionary(dictionary.getWords());
    }

    /** Конструктор для тестирования
     * @param dictionary словарь
     * @param rightAnswer искуственно правильно заданный ответ
     * @param logFile логфайл
     */
    protected WordleGame (WordleDictionary dictionary,String rightAnswer, PrintWriter logFile) {
        this.dictionary = dictionary;
        this.logFile = logFile;
        this.currentStep = 6;
        this.rightAnswer = rightAnswer;
        this.gameStatus = WordleGameStatus.IN_PROGRESS;
        this.hints = new WordleDictionary(dictionary.getWords());
    }

    /** Метод анализирует ответ пользователя и возвращает результат
     * @param userAnswer слово введенное пользователем
     * @return строку с посимвольным результатом сравнения
     */
    public String compareUserAnswer(String userAnswer) {
        userAnswer = dictionary.normalize(userAnswer);
        if (rightAnswer.equals(userAnswer)) {
            gameStatus = WordleGameStatus.SUCCESS;
            logFile.println("Пользователь угадал");
        } else {
            currentStep++;
            if (currentStep > maxSteps) {
                gameStatus = WordleGameStatus.FAIL;
                logFile.println("Пользователь проиграл");
            }
        }
        return getResult(userAnswer);
    }

    /** Метод выполняет проверки введенного пользователем слова согласно ТЗ
     *  Проверять длину слова, пустоту, наличие английских символов и наличие в словаре
     * @param word введенное пользователем слово
     */
    public void checkWord(String word) throws WordNotFoundInDictionary, WordNullOrIncorrectLength, WordHasNotCirilicChar {
        logFile.println("Проверяется слово: " + word);
        if (!word.matches(".*[а-яА-ЯёЁ].*")) {
            throw new WordHasNotCirilicChar();
        }
        if (word == null || (word.length() != wordLength)) {
            throw new WordNullOrIncorrectLength("Слово либо не соответствует необходимой длине (" +
                    wordLength + "), либо пусто.");
        }
        if (!dictionary.getWords().contains(word)) {
            throw new WordNotFoundInDictionary();
        }
        logFile.println("Проверки пройдены успешно.");
    }

    /** Метод сравнивает посимвольно правильный ответ и ответ пользователя
     * @param userAnswer слово введенное пользователем
     * @return результат сравнения согласно правилам игры
     */
    private String getResult(String userAnswer) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < rightAnswer.length(); i++) {
            CharSequence charSequence = String.valueOf(userAnswer.charAt(i));
            if (userAnswer.charAt(i) == rightAnswer.charAt(i)) {
                result.append("+");
                // удаляем из словаря подсказок все слова в которых нет указанной буквы на заданной позиции
                hints.deleteWordCharNotOnPosition(userAnswer.charAt(i),i);
            } else if (rightAnswer.contains(charSequence)) {
                result.append("^");
                // удаляем из словаря подсказок все слова в которых нет указанной буквы
                hints.deleteWordNotContainsThisChar(charSequence);
                // удаляем из словаря подсказок все слова, где указанная буква на заданной позиции
                hints.deleteWordCharOnPosition(userAnswer.charAt(i),i);
            } else {
                result.append("-");
                // удаляем из словаря подсказок все слова в которых есть указанная буква
                hints.deleteWordWithChar(charSequence);
            }
        }
        return result.toString();
    }

    /** Метод генерирует и выдаёт случайное слово из словаря подсказок
     * @return слово подсказка
     */
    public String getHint() {
        String hint = hints.getRandomWord();
        hints.remove(hint); // удаляем слово подсказку из словаря, что бы более не встречалась
        return hint;
    }
}
