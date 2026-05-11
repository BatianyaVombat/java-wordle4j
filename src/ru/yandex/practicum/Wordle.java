package ru.yandex.practicum;

public class Wordle {
    public static final WordleLogger wordleLogger = new WordleLogger();

    public static void main(String[] args) {
        wordleLogger.initFileLogging("wordle.log");
        try {
            WordleDictionary wDictionary = new WordleDictionary("src/words_ru.txt");
            WordleGame wg = new WordleGame(wDictionary);
            wg.startGame();
            WordleLogger.WordleLoggerClose();
        } catch (Exception e) {
            System.out.println("Проблема со словарём! Поиграем в другой раз!");
        }
    }
}
